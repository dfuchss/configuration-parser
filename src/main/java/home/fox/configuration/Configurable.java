package home.fox.configuration;

import home.fox.configuration.annotations.AfterSetting;
import home.fox.configuration.annotations.NoSet;
import home.fox.configuration.annotations.SetterInfo;
import home.fox.configuration.setters.ResourceBundleSetter;

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
