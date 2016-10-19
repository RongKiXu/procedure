package com.rongki.util;

/**
 * �洢���̴��ι�����
 */
public class ProcedureParameter {
	private String name;
	private Object value;
	private int type;
	private boolean isIn;/*�Ƿ����*/

	public ProcedureParameter(String name, Object value, int type, boolean isIn) {
		this.name = name;
		this.value = value;
		this.type = type;
		this.isIn = isIn;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public Object getValue() {
		return value;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}

	public void setIn(boolean isIn) {
		this.isIn = isIn;
	}

	public boolean isIn() {
		return isIn;
	}
}
