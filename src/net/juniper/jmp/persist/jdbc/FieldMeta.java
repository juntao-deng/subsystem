package net.juniper.jmp.persist.jdbc;

import java.lang.reflect.Method;

import org.apache.commons.beanutils.Converter;
/**
 * Field information for table column and entity field mapping
 * @author juntaod
 *
 */
public class FieldMeta {
	private String field;
	private Class<?> fieldType;
	private String column;
	private boolean nullable;
	private Integer columnType;
	private Method readRef;
	private Method writeRef;
	private Converter converter;
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getColumn() {
		return column;
	}
	public void setColumn(String column) {
		this.column = column;
	}
	public boolean isNullable() {
		return nullable;
	}
	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}
	public Method getReadRef() {
		return readRef;
	}
	public void setReadRef(Method readRef) {
		this.readRef = readRef;
	}
	public Method getWriteRef() {
		return writeRef;
	}
	public void setWriteRef(Method writeRef) {
		this.writeRef = writeRef;
	}
	public Integer getColumnType() {
		return columnType;
	}
	public void setColumnType(Integer columnType) {
		this.columnType = columnType;
	}
	public Converter getConverter() {
		return converter;
	}
	public void setConverter(Converter converter) {
		this.converter = converter;
	}
	public Class<?> getFieldType() {
		return fieldType;
	}
	public void setFieldType(Class<?> fieldType) {
		this.fieldType = fieldType;
	}
}
