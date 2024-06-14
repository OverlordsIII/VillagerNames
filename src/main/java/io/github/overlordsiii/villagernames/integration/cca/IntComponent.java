package io.github.overlordsiii.villagernames.integration.cca;

import org.ladysnake.cca.api.v3.component.Component;

public interface IntComponent extends Component {
	int getValue();
	void setValue(int value);
}
