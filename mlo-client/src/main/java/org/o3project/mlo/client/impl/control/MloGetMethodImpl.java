/**
 * MloGetMethodImpl.java
 * (C) 2014,2015, Hitachi, Ltd.
 */
package org.o3project.mlo.client.impl.control;

/**
 * This class is the implementation class of GET method of {@link org.o3project.mlo.client.control.MloMethod}.
 */
public class MloGetMethodImpl extends MloBaseMethod {

	/* (non-Javadoc)
	 * @see org.o3project.mlo.client.control.MloMethod#getName()
	 */
	@Override
	public String getName() {
		return "GET";
	}

	/* (non-Javadoc)
	 * @see org.o3project.mlo.client.control.MloMethod#isSetDoOutput()
	 */
	@Override
	public boolean isSetDoOutput() {
		return false;
	}
}
