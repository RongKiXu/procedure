package com.rongki.util;

import java.text.DecimalFormat;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

public class JsonNumberValueProcessorImpl implements JsonValueProcessor {
	
	private String format = "#0.00";

	public JsonNumberValueProcessorImpl() {

	}

	public JsonNumberValueProcessorImpl(String format) {
		this.format = format;
	}

	public Object processArrayValue(Object value, JsonConfig jsonConfig) {
		String[] obj = {};
		if (value instanceof Double[]) {
			DecimalFormat sf = new DecimalFormat(format);
			Double[] doubles = (Double[]) value;
			obj = new String[doubles.length];
			for (int i = 0; i < doubles.length; i++) {
				obj[i] = sf.format(doubles[i]);
			}
		}
		return obj;
	}

	public Object processObjectValue(String key, Object value,
			JsonConfig jsonConfig) {
		if (null != value) {
			if (value instanceof Double) {
				String str = new DecimalFormat(format).format((Double) value);
				return str;
			}
			return value.toString();
		} else {
			return null;
		}
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}
}
