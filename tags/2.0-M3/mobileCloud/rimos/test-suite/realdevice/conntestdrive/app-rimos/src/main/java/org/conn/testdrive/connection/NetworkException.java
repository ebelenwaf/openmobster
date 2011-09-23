/**
 * Copyright (c) {2003,2010} {openmobster@gmail.com} {individual contributors as indicated by the @authors tag}.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.conn.testdrive.connection;

import org.conn.testdrive.errors.BaseException;

/**
 * 
 * @author openmobster@gmail.com
 *
 */
public final class NetworkException extends BaseException 
{
	/**
	 * 
	 * @param className
	 * @param methodName
	 * @param parameters
	 */
	public NetworkException(String className, String methodName, Object[] parameters)
	{
		super(className, methodName, parameters);
	}
}