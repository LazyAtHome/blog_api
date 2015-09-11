package ${pkg};

import com.landaojia.blogserver.common.dao.BaseEO;
#foreach( ${import} in ${table.imports} )
import ${import};
#end

/**
 * ${table.comment}
 * 
 * Auto Generated by <code>CodeGenerator</code>
 */
public class ${table.className} extends BaseEO {
	private static final long serialVersionUID = 1L;
#foreach( ${col} in ${table.cols} )

	private ${col.javaType} ${col.fieldName};//${col.comment}
#if( ${col.needInCond} )

	transient private List<${col.javaType}> ${col.fieldName}s;//${col.comment}
#end
#end
#foreach( ${col} in ${table.cols} )

	/**
	 * return ${col.comment}
	 */
	public ${col.javaType} ${col.getterName}() {
		return ${col.fieldName};
	}

	/**
	 * @param ${col.fieldName}
	 *            ${col.comment}
	 */
	public void ${col.setterName}(${col.javaType} ${col.fieldName}) {
		this.${col.fieldName} = ${col.fieldName};
	}
#if( ${col.needInCond} )
	
	/**
	 * return ${col.comment}s
	 */
	public List<${col.javaType}> ${col.getterName}s() {
		return ${col.fieldName}s;
	}

	/**
	 * @param ${col.fieldName}s
	 *            ${col.comment}
	 */
	public void ${col.setterName}s(List<${col.javaType}> ${col.fieldName}s) {
		this.${col.fieldName}s = ${col.fieldName}s;
	}
#end
#end
}
