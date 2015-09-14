package com.landaojia.blog.common.util;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author codingwoo <long1795@gmail.com>
 */
public final class Strings {
	private Strings() {
	}

	public static String getUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	public static String getUUID(String prefix) {
		return prefix + getUUID();
	}

	public static String unicode2String(String str) {
		Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
		Matcher matcher = pattern.matcher(str);
		char ch;
		while (matcher.find()) {
			ch = (char) Integer.parseInt(matcher.group(2), 16);
			str = str.replace(matcher.group(1), ch + "");
		}
		return str;
	}

	public static String format(String pattern, Object o) {
		if (o == null) {
			return null;
		}
		if (pattern == null) {
			return o.toString();
		}
		if (o instanceof Date) {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			return sdf.format(o);
		}
		if (o instanceof Long) {
			Long l = (Long) o;
			DecimalFormat df = new DecimalFormat(pattern);
			return df.format(l);
		}
		return o.toString();
	}

	public static Date parse(String pattern, String date) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.parse(date);
	}

	/**
	 * 将驼峰式命名的字符串转换为下划线小写方式。如果转换前的驼峰式命名的字符串为空，则返回空字符串。</br>
	 * 例如：HelloWorld->hello_world
	 * 
	 * @param name
	 *            转换前的驼峰式命名的字符串
	 * @return 转换后下划线小写方式命名的字符串
	 */
	public static String underscoreName(String name) {
		StringBuilder result = new StringBuilder();
		if (name == null || name.isEmpty()) {
			return "";
		}
		result.append(name.charAt(0));
		char[] cs = name.substring(1).toCharArray();
		// 循环处理其余字符
		for (char c : cs) {
			if (c >= 'A' && c <= 'Z') {
				result.append("_");
			}
			result.append(c);
		}
		return result.toString().toLowerCase();
	}

	public static String camelName(String name, String prefix, boolean isFirstLower) {
		if (prefix != null && !prefix.isEmpty() && name.startsWith(prefix)) {
			name = name.replaceFirst(prefix, "");
		}
		return camelName(name, isFirstLower);
	}

	public static String camelName(String name) {
		return camelName(name, true);
	}

	/**
	 * 将下划线方式命名的字符串转换为驼峰式。如果转换前的下划线方式命名的字符串为空，则返回空字符串。</br>
	 * 例如：HELLO_WORLD->HelloWorld
	 * 
	 * @param name
	 *            转换前的下划线大写方式命名的字符串
	 * @return 转换后的驼峰式命名的字符串
	 */
	public static String camelName(String name, boolean isFirstLower) {
		StringBuilder result = new StringBuilder();
		if (name == null || name.isEmpty()) {
			return "";
		}
		if (!name.contains("_")) {
			return camelNameImpl(name, isFirstLower);
		}
		String camels[] = name.split("_");
		for (String camel : camels) {
			if (camel.isEmpty()) {
				continue;
			}
			if (result.length() == 0) {
				// 第一个驼峰片段，全部字母都小写
				result.append(camelNameImpl(camel, isFirstLower));
			} else {
				// 其他的驼峰片段，首字母大写
				result.append(camelNameImpl(camel, false));
			}
		}
		return result.toString();
	}

	private static String camelNameImpl(String name, boolean isFirstLower) {
		if (isFirstLower) {
			return name.substring(0, 1).toLowerCase() + name.substring(1);
		}
		return name.substring(0, 1).toUpperCase() + name.substring(1);
	}

	public static boolean isNullOrEmpty(String pattern) {
		return pattern == null || pattern.trim().length() == 0;
	}
	
	
    public static boolean isNullOrEmpty(String... strings) {
        for (String string : strings) {
            if (string == null || string.trim().equals("")) {
                return true;
            }
        }
        return false;
    }

    public static String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }
}
