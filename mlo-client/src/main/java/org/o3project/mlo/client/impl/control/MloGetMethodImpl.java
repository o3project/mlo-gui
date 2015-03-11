/**
 * MloGetMethodImpl.java
 * (C) 2014, Hitachi Solutions, Ltd.
 */
package org.o3project.mlo.client.impl.control;

/**
 * {@link org.o3project.mlo.client.control.MloMethod} の GETメソッド実装クラスです。
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
