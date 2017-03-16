package org.fuchss.configuration;

import org.fuchss.configuration.annotations.AfterSetting;
import org.fuchss.configuration.annotations.NoSet;
import org.fuchss.configuration.annotations.SetterInfo;
import org.fuchss.configuration.setters.ResourceBundleSetter;

/**
 * This interface has to be implemented by classes, if their values shall be set
 * via a {@link Setter}.
 *
 * @author Dominik Fuchss
 * @see SetterInfo
 * @see AfterSetting
 * @see NoSet
 * @see ResourceBundleSetter
 *
 */
public interface Configurable {
}
