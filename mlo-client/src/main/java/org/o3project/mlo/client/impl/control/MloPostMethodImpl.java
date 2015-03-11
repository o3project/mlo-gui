/**
 * MloPostMethodImpl.java
 * (C) 2014, Hitachi Solutions, Ltd.
 */
package org.o3project.mlo.client.impl.control;

/**
 * {@link org.o3project.mlo.client.control.MloMethod} の POSTメソッド実装クラスです。
 */
public class MloPostMethodImpl extends MloBaseMethod {

	/* (non-Javadoc)
	 * @see org.o3project.mlo.client.control.MloMethod#getName()
	 */
	@Override
	public String getName() {
		return "POST";
	}

	/* (non-Javadoc)
	 * @see org.o3project.mlo.client.control.MloMethod#isSetDoOutput()
	 */
	@Override
	public boolean isSetDoOutput() {
		return true;
	}
}
