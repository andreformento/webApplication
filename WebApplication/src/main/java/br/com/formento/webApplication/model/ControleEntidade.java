package br.com.formento.webApplication.model;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

public class ControleEntidade<T> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5880719297583911734L;

	private final Object objectSuperClass;

	private Collection<String> camposTabela;
	private String nomeTabela;
	private Class<T> entidadeClass;

	public ControleEntidade(Object objectSuperClass) {
		this.objectSuperClass = objectSuperClass;
	}

	public T getNovaEntidade() {
		T novaEntidade = null;
		try {
			novaEntidade = getEntidadeClass().newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return novaEntidade;
	}

	@SuppressWarnings("unchecked")
	private Class<T> gerarEntidade() {
		Class<?> thisClass = objectSuperClass.getClass();
		Type genericSuperclass = thisClass.getGenericSuperclass();
		ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;

		Type type = parameterizedType.getActualTypeArguments()[0];
		Class<T> entidadeGerada = (Class<T>) type;
		return entidadeGerada;
	}

	public Class<T> getEntidadeClass() {
		if (entidadeClass == null)
			entidadeClass = gerarEntidade();
		return entidadeClass;
	}

	public Collection<String> getCamposTabela() {
		if (camposTabela == null) {
			camposTabela = new HashSet<String>();

			Set<String> fieldsConsiderados = new HashSet<>();

			Set<String> fieldNames = new HashSet<>();
			List<Field> fields = new ArrayList<>();

			for (Field field : getEntidadeClass().getDeclaredFields()) {
				if (!java.lang.reflect.Modifier.isStatic(field.getModifiers())) {
					Transient annotationTransient = field.getAnnotation(Transient.class);
					if (annotationTransient == null) {
						if (Collection.class.isAssignableFrom(field.getType()))
							continue;

						fields.add(field);
					}
				}
			}

			for (Field field : fields)
				fieldNames.add(field.getName());

			Method[] declaredMethods = getEntidadeClass().getDeclaredMethods();
			for (Method method : declaredMethods) {
				method.setAccessible(true);
				if (method.getAnnotation(Transient.class) != null)
					continue;
				if (method.getAnnotation(OneToMany.class) != null)
					continue;
				if (method.getAnnotation(ManyToMany.class) != null)
					continue;
				if (method.getParameterTypes().length > 0)
					continue;

				String nomeField = method.getName();
				if (nomeField.substring(0, 3).equalsIgnoreCase("get")) {
					String primeiraLetra = nomeField.substring(3, 4).toLowerCase();
					nomeField = primeiraLetra + nomeField.substring(4);
					fieldsConsiderados.add(nomeField);

					Column annotationColumn = method.getAnnotation(Column.class);
					JoinColumn annotationJoinColumn = method.getAnnotation(JoinColumn.class);

					if (annotationColumn != null)
						camposTabela.add(annotationColumn.name());
					else if (annotationJoinColumn != null)
						camposTabela.add(annotationJoinColumn.name());
					else
						camposTabela.add(nomeField);
				}
			}

			// campos da classe anotadas como @Id
			for (Field field : fields) {
				String nomeField = field.getName();
				if (!fieldsConsiderados.contains(nomeField)) {
					Column annotationColumn = field.getAnnotation(Column.class);
					if (annotationColumn == null)
						camposTabela.add(nomeField);
					else
						camposTabela.add(annotationColumn.name());
				}
			}
		}
		return camposTabela;
	}

	public String getCamposTabelaSQL(String alias) {
		StringBuilder stringBuilder = new StringBuilder();
		for (String campo : getCamposTabela()) {
			if (stringBuilder.length() > 0)
				stringBuilder.append(",");

			if (alias != null)
				stringBuilder.append(alias);

			stringBuilder.append(campo);
		}

		return stringBuilder.toString();
	}

	public boolean getChavePrimariaPreenchida(T entidade) {
		if (entidade == null)
			return false;

		boolean isChavePreenchida = false;

		// métodos da classe anotadas como @Id
		Method[] declaredMethods = getEntidadeClass().getDeclaredMethods();
		for (Method method : declaredMethods) {
			Id annotation = method.getAnnotation(Id.class);
			if (annotation != null) {
				method.setAccessible(true);
				try {
					Object object = method.invoke(entidade);

					if (object == null)
						return false;
					else
						isChavePreenchida = true;
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}

		// campos da classe anotadas como @Id
		Field[] fields = getEntidadeClass().getDeclaredFields();
		for (Field field : fields) {
			Id annotation = field.getAnnotation(Id.class);
			if (annotation != null) {
				field.setAccessible(true);
				Object object;
				try {
					object = field.get(entidade);

					// se tiver alguma chave null cai fora como false
					if (object == null)
						return false;
					else
						isChavePreenchida = true;
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}

		return isChavePreenchida;
	}

	public String getNomeTabela() {
		if (nomeTabela == null) {
			Table table = getEntidadeClass().getAnnotation(Table.class);
			if (table == null)
				nomeTabela = getEntidadeClass().getSimpleName();
			else
				nomeTabela = table.name();
		}
		return nomeTabela;
	}

	public String getTipo() {
		return getNomeTabela().substring(3);
	}

}
