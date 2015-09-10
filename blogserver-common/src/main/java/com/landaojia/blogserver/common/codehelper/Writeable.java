package com.landaojia.blogserver.common.codehelper;

import com.landaojia.blogserver.common.log.LogAble;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;

/**
 * @author codingwoo <long1795@gmail.com>
 */
public interface Writeable extends LogAble {

	default void write(VelocityContext vc, String template, String output) {
		FileWriter out = null;
		InputStreamReader inputStreamReader = null;
		try {
			inputStreamReader = new InputStreamReader(new FileInputStream(template));
			out = new FileWriter(new File(output));

			Velocity.init();
			VelocityEngine ve = new VelocityEngine();
			ve.evaluate(vc, out, "velocity", inputStreamReader);
		} catch (Exception e) {
			log().error(e.getMessage(), e);
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (inputStreamReader != null) {
					inputStreamReader.close();
				}
			} catch (Exception e) {
				log().error(e.getMessage(), e);
			}
		}
	}

}
