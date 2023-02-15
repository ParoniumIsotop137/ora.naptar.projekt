package ora.naptar;

public class OraNaptarMain {

	/**
	 * @wbp.parser.entryPoint
	 */
	public static void main(String[] args) {

		OraNaptar app = new OraNaptar();
		app.getFrmOra().setVisible(true);
		app.OraKijelzes();

	}

}
