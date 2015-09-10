package ${s.pkg};

import com.landaojia.base.common.fsm.ActionNotSupportedException;
import com.landaojia.base.common.fsm.State;
import com.landaojia.base.utils.log.LogAble;

/**
 * @author Gray <b>long1795@gmail.com</b>
 */
public class ${s.name}State extends State implements LogAble {
	private static final long serialVersionUID = 1L;

	/**
	 * @param code
	 * @param name
	 */
	public ${s.name}State(Integer code, String name) {
		super(code, name);
	}

	protected void entry(${s.name}Context ctx) {
	}

	protected void exit(${s.name}Context ctx) {
	}

	//
#foreach( ${f} in ${s.actions} )
	protected void ${f}(${s.name}Context ctx) {
		handle(ctx);
	}

#end
	//
	void handle(${s.name}Context ctx) {
		log().error("Action is not accpected by Stat[{}:{}]", code, name);
		throw new ActionNotSupportedException();
	}
}
