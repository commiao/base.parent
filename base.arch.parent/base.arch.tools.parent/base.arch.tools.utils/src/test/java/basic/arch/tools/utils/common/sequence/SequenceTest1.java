package basic.arch.tools.utils.common.sequence;

import org.junit.Test;

import basic.arch.tools.utils.common.sequence.Sequence;

public class SequenceTest1 {

	@Test
	public void name() {
		Sequence sequence = new Sequence(0, 0);
		for (int i = 0; i < 1000; i++) {
			long id = sequence.nextId();
			System.out.println(id);
		}
	}

}
