package ch.zhaw.musictagssearch.example;

public class CommandWindow implements Output{

	@Override
	public void outputResult(StringBuilder builder) {
		System.out.println(builder.toString());
	}

	@Override
	public void outputInfo(String info) {
		System.out.println(info);
	}

	@Override
	public void clearOutput() {
		//TODO
	}


}
