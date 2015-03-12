/**
 * JavaFxFUnit4ClassRunner.java
 * (C) 2014,2015, Hitachi, Ltd.
 */
package org.o3project.mlo.client.test;

import java.util.concurrent.CountDownLatch;

import javafx.application.Platform;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.internal.runners.InitializationError;
import org.junit.internal.runners.JUnit38ClassRunner;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;

/**
 * JavaFxFUnit4ClassRunner
 *
 */
public class JavaFxJUnitRunner extends Runner {
	private static final Log LOG = LogFactory.getLog(JavaFxJUnitRunner.class);
	
	private final Runner delegate;
	
	/**
	 * @throws InitializationError 
	 * 
	 */
	public JavaFxJUnitRunner(final Class<?> clazz) throws InitializationError {
		super();
		LOG.info(clazz.getName());
		delegate = RunnerFactory.createRunnerFor(clazz);
		JavaFxJUnitApplication.startJavaFx();
	}

	/* (non-Javadoc)
	 * @see org.junit.runner.Runner#getDescription()
	 */
	@Override
	public Description getDescription() {
		return delegate.getDescription();
	}

	/* (non-Javadoc)
	 * @see org.junit.runner.Runner#run(org.junit.runner.notification.RunNotifier)
	 */
	@Override
	public void run(final RunNotifier notifier) {
		final CountDownLatch latch = new CountDownLatch(1);
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				JavaFxJUnitRunner.this.delegate.run(notifier);

				latch.countDown();
			}
		});
		try {
			latch.await();
		} catch (InterruptedException e) {
			LOG.warn(e);
		}
	}
	
	/* (non-Javadoc)
	 * @see org.junit.runner.Runner#testCount()
	 */
	@Override
	public int testCount() {
		return delegate.testCount();
	}
}

class RunnerFactory {
	 
    public static Runner createRunnerFor(Class<?> clazz) throws InitializationError {
        if (!shouldSkipClass() && isSubclassOfTestCase(clazz)) {
            return new JUnit38ClassRunner(clazz);
        }
 
        return new JUnit4ClassRunner(clazz);
    }
 
    private static boolean isSubclassOfTestCase(Class<?> clazz) {
        return TestCase.class.isAssignableFrom(clazz);
    }
 
    public static boolean shouldSkipClass() {
        return System.getProperty("ExecuteIntegrationTests") == null;
    }
 
}
