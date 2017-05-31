package unidad2;

public class ManejoDeFuncion {
	
	private String funcion;
	private int n;
	private double XAnterior, Es, Xi, Xf, Xm, Xr, Ea, fXi, fXf, fXr, fXm;
	private char signofXi, signofXf, signofXm, SignofXr;
	private static double redondeo = 10000.00; // 4 digitos
	
	/*
	 * Constructor que recibe una funcion String y un entero n. 
	 * Todos los demas datos se inicializan por defecto.
	 * 
	 */
	public ManejoDeFuncion(String f, int n) 
	{
		this.funcion = f;
		this.n = n;
		// Busca las 'x' minusculas y las hace mayusculas ('X')
		this.funcion = funcion.replace('x', 'X');
		this.Ea=10000;
		this.Xm=0;
		this.Xi=0;
		this.Xf=1;
		this.tabularSignoXiyXm();
		this.calcularEs();
	}
	
	/*
	 * Constructor de la clase que recibe una funcion, un entero n, el Xi y el Xf
	 * Ea y Xm se inicializan por defecto.
	 * 
	 */
	public ManejoDeFuncion(String f, int n, double xi, double xf) 
	{
		this.funcion = f;
		this.n = n;
		// Busca las 'x' minusculas y las hace mayusculas ('X')
		this.funcion = funcion.replace('x', 'X');
		this.Ea=10000;
		this.Xm=0;
		this.Xi=xi;
		this.Xf=xf;
		this.calcularEs();
	}
	
	/*
	 * Comprueba en la posición del entero recibido en la función por defecto si es un número, un punto o una coma.
	 * Si el caracter de la posición dada es un numero, punto o coma retorna verdadero.
	 * Si es una letra o cualquier otro signo retorna falso.
	 * 
	 */
	public boolean isNumber(int a) {
		return this.isNumber(a, funcion);
	}
	
	
	/*
	 * Comprueba en la posición del entero recibido de la función dad si es un número, un punto o una coma.
	 * Si el caracter de la posición dada es un numero, punto o coma retorna verdadero.
	 * Si es una letra o cualquier otro signo retorna falso.
	 */
	public boolean isNumber(int a, String funcion)
	{
		// Si la variable 'a' es igual a -1 o mayor o igual al tamaño de la función retorna falso.
		if(a==-1 || a >= funcion.length())
			return false;
		
		// Busca un caracter de la función dada. Si es número, punto o coma retorna true de lo contrario false
		switch(funcion.charAt(a))
		{
			case '.':
			case ',':
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
			case 'P':
				return true;
			default:
				return false;
		}
	}
	
	/*
	 * Método que comprueba que sea un operador la posición recibida en la función por defecto.
	 */
	public boolean isOperator(int posi_inicial) {
		return this.isOperator(posi_inicial, funcion);
	}
	
	/*
	 * Compruebea si la funcion dada es un operador comparando desde la posición dada
	 */
	public boolean isOperator(int posi_inicial, String funcion)
	{
		System.out.println(posi_inicial);
		System.out.println(funcion.length());
		if(posi_inicial >= funcion.length())
			return false;
		
		switch(funcion.charAt(posi_inicial))
		{
			case '^':
			case '+':
			case '-':
			case '*':
			case '/':
				return true;
			default:
				return false;
		}
	}
	
	/*
	 * Comprueba si es una función trigonometrica recibiendo el valor a comprobar con la funcion por defecto
	 */
	public String isFuncTrigonometry(int i) {
		return this.isFuncTrigonometry(i, funcion);
	}
	
	/*
	 * Comprueba que sea una función trigonometrica en la posiciónn dada y en la funcion dada
	 */
	public String isFuncTrigonometry(int i, String funcion) 
	{
		if(funcion.charAt(i)=='S' && funcion.charAt(i+1)=='E' && funcion.charAt(i+2)=='N')
			return "SEN";
		
		if(funcion.charAt(i)=='C' && funcion.charAt(i+1)=='O' && funcion.charAt(i+2)=='S')
			return "COS";
		
		if(funcion.charAt(i)=='T' && funcion.charAt(i+1)=='A' && funcion.charAt(i+2)=='N')
			return "TAN";
		
		return null;
	}
	
	/*
	 * Regresa el número completo de la posición dada en la función por defecto
	 */
	public String capturarNumCompleto(int posi_inicial)	{
		return this.capturarNumCompleto(posi_inicial, funcion, "");
	}
	
	/*
	 * Regresa el número completo de la posición dada en el sentido dado en la función por defecto
	 */
	public String capturarNumCompleto(int posi_inicial, String sentido) {
		return this.capturarNumCompleto(posi_inicial, funcion, sentido);
	}
	
	/*
	 * Regresa el número completo en el primer operador encontrado en el sentido dado en la función dada
	 */
	public String capturarNumComp(char operator, String funcion, String sentido) 
	{
		String num="";
		for(int i=0; i<funcion.length(); i++) 
		{
			if(funcion.charAt(i) == operator)
			{
				if(i == 0 && sentido == "IZQUIERDA")
					num += "-";
				
				if(i!=0)
				{
					System.out.println("i:"+i+" funcion: "+funcion);
					num += this.capturarNumCompleto(i, funcion, sentido);
					break;
				}
			}
		}
		System.out.println("num:"+num);
		return num;
	}
	
	public String capturarNumCompleto(int posi_inicial, String funcion, String sentido)
	{
		String numCompleto="";
		sentido.toUpperCase();
		int a;
		if(this.isOperator(posi_inicial, funcion))
		{
			if(sentido.equals("DERECHA"))
			{
				a = posi_inicial+1;
				while(this.isNumber(a, funcion))
				{
					numCompleto += funcion.charAt(a);
					a++;
				}
				System.out.println(numCompleto);
			}
			else if(sentido.equals("IZQUIERDA"))
			{
				a = posi_inicial-1;
				String numCompletoInvertido="";	
				while(this.isNumber(a, funcion))
				{
					numCompletoInvertido += funcion.charAt(a);
					a--;
				}
				
				// Invertimos el numero porque se captura de forma contraria (solo si contiene 2 o más numeros de longuitud)
				if(numCompletoInvertido.length() > 1)
				{
					for (int x=numCompletoInvertido.length()-1;x>=0;x--)
						numCompleto = numCompleto + numCompletoInvertido.charAt(x);
				} else
					numCompleto = numCompletoInvertido;
				
				System.out.println(numCompleto);
			} 
			else if(sentido.isEmpty() || sentido.equals(""))
			{
				if(funcion.charAt(posi_inicial) == '^' || funcion.charAt(posi_inicial-1) == 'X') 
				{
					return this.capturarNumCompleto(posi_inicial, funcion, "DERECHA");
				}
				else if(funcion.charAt(posi_inicial) == '+' || funcion.charAt(posi_inicial) == '-' || funcion.charAt(posi_inicial) == '*' || funcion.charAt(posi_inicial) == '/')
				{
					return this.capturarNumCompleto(posi_inicial, funcion, "IZQUIERDA");
				}
			}
		}
		System.out.println("numCompleto:"+numCompleto);
		return numCompleto;
	}

	/*
	 * Sustituye X en la función y realiza dicha operación
	 */
	public double calcularFuncion(double valor)
	{
		double operacion=0;
		int vuelta=0;
		
		String nuevaFuncion=funcion;
		int posi_inicial;
		String numCompleto;
		while(vuelta<4)
		{
			System.out.println("Vuelta:"+vuelta+" funcion: "+funcion+" NFUNCION: "+nuevaFuncion);
			for(int i=0; i<funcion.length(); i++)
			{
				// Vuelta para operaciones trigonométricas
				if(vuelta==0)
				{
					if(funcion.charAt(i) == 'S')
					{
						if(this.isFuncTrigonometry(i).equals("SEN"))
						{
							if(funcion.charAt(i+3) == '(')
							{
								
							}
							else if(funcion.charAt(i+3) == 'X' && (funcion.length() >= i+4 || this.isOperator(i+4)))
							{
								operacion = Math.sin(valor);
								System.out.println(nuevaFuncion);
								nuevaFuncion = nuevaFuncion.replace("SENX", operacion+"");
								System.out.println(nuevaFuncion);
							} 
							else
							{
								
							}
								
						}
					} 
					else if(funcion.charAt(i) == 'C')
					{
						if(this.isFuncTrigonometry(i).equals("COS"))
						{
							if(funcion.charAt(i+3) == '(')
							{
								
							}
							else if(funcion.charAt(i+3) == 'X' && (funcion.length() >= i+4 || this.isOperator(i+4)))
							{
								operacion = Math.cos(valor);
								System.out.println(nuevaFuncion);
								nuevaFuncion = nuevaFuncion.replace("COSX", operacion+"");
								System.out.println(nuevaFuncion);
							} 
							else
							{
								
							}	
						}
					}
					else if(funcion.charAt(i) == 'T')
					{
						if(this.isFuncTrigonometry(i).equals("TAN"))
						{
							if(funcion.charAt(i+3) == '(')
							{
								
							}
							else if(funcion.charAt(i+3) == 'X' && (funcion.length() >= i+4 || this.isOperator(i+4)))
							{
								operacion = Math.tan(valor);
								System.out.println(nuevaFuncion);
								nuevaFuncion = nuevaFuncion.replace("TANX", operacion+"");
								System.out.println(nuevaFuncion);
							} 
							else
							{
								
							}
						}
					}
				}
				// Vuelta para buscar exponentes
				else if(vuelta==1)
				{
					if(funcion.charAt(i) == '^')
					{
						if(funcion.charAt(i-1) == 'X')
						{
							if(funcion.charAt(i+1) == '-')
								posi_inicial=i+1;
							else
								posi_inicial=i;
							
							numCompleto = this.capturarNumCompleto(posi_inicial);
							if(numCompleto.equals("P"))
								operacion = Math.pow(valor, Math.PI);
							else
								operacion = Math.pow(valor, Double.parseDouble(numCompleto));
							nuevaFuncion = nuevaFuncion.replace("X^"+numCompleto, operacion+"");
							System.out.println(nuevaFuncion); //----------------------
						}
						else if(funcion.charAt(i-1) == 'e')
						{
							if(funcion.charAt(i+1) == 'X')
							{
								operacion = Math.pow(Math.E, valor);
								nuevaFuncion = nuevaFuncion.replace("e^X", operacion+"");
							}
							else if(funcion.charAt(i+1) == '-')
							{
								if(funcion.charAt(i+2) == 'X')
								{
									operacion = Math.pow(Math.E, -valor);
									nuevaFuncion = nuevaFuncion.replace("e^-X", operacion+"");
								}
							}
						}
					}
				}
				// Vuelta para buscar multiplicaciones
				else if(vuelta==2) 
				{
					if(funcion.charAt(i) == '*')
					{
						if(funcion.charAt(i+1) == 'X' && funcion.charAt(i+2) != '^')
						{	
							System.out.println("FIRST NF1: "+nuevaFuncion); //----------------------
							numCompleto = this.capturarNumCompleto(i);
							if(numCompleto.equals("P"))
								operacion = Math.PI*valor;
							else
								operacion = Double.parseDouble(numCompleto)*valor;
							nuevaFuncion = nuevaFuncion.replace(numCompleto+"*X", operacion+"");
							System.out.println("LAST NF1: "+nuevaFuncion); //----------------------
						}
						else
						{
							System.out.println("FIRST NF: "+nuevaFuncion); //----------------------
							numCompleto = this.capturarNumComp('*', nuevaFuncion, "IZQUIERDA");
							String numCompleto2 = this.capturarNumComp('*', nuevaFuncion, "DERECHA");
							if(numCompleto.equals("P"))
								operacion = Math.PI*Double.parseDouble(numCompleto2);
							else if (numCompleto2.equals("P"))
								operacion = Double.parseDouble(numCompleto)*Math.PI;
							else
								operacion = Double.parseDouble(numCompleto)*Double.parseDouble(numCompleto2);
							nuevaFuncion = nuevaFuncion.replace(numCompleto+"*"+numCompleto2, operacion+"");
							System.out.println("LAST NF: "+nuevaFuncion); //----------------------
						}
					}
					else if(funcion.charAt(i) == '/')
					{
						if(funcion.charAt(i+1) == 'X')
						{
							numCompleto = this.capturarNumCompleto(i);
							if(numCompleto.equals("P"))
								operacion = Math.PI/valor;
							else
								operacion = Double.parseDouble(numCompleto)/valor;
							nuevaFuncion = nuevaFuncion.replace(numCompleto+"/X", operacion+"");
						}
						else if(funcion.charAt(i-1) == 'X')
						{
							numCompleto = this.capturarNumCompleto(i, "DERECHA");
							if(numCompleto.equals("P"))
								operacion = valor/Math.PI;
							else
								operacion = valor/Double.parseDouble(numCompleto);
							nuevaFuncion = nuevaFuncion.replace("X/"+numCompleto, operacion+"");
						}
						else
						{
							numCompleto = this.capturarNumComp('/', nuevaFuncion, "IZQUIERDA");
							String numCompleto2 = this.capturarNumComp('/', nuevaFuncion, "DERECHA");
							if(numCompleto.equals("P"))
								operacion = Math.PI/Double.parseDouble(numCompleto2);
							else if (numCompleto2.equals("P"))
								operacion = Double.parseDouble(numCompleto)/Math.PI;
							else
								operacion = Double.parseDouble(numCompleto)/Double.parseDouble(numCompleto2);
							nuevaFuncion = nuevaFuncion.replace(numCompleto+"/"+numCompleto2, operacion+"");
						}
					}
				}
				// Vuelta para buscar sumas y restas
				else if(vuelta==3)
				{
					String cantidad1, cantidad2;
					if(funcion.charAt(i) == '+' || funcion.charAt(i) == '-')
					{
						/*
						if(funcion.charAt(i+1) == 'X')
						{
							cantidad1 = this.capturarNumComp(funcion.charAt(i), nuevaFuncion, "IZQUIERDA");
							System.out.println("cantidad1: "+cantidad1); // -----------------------------
							if(funcion.charAt(i) == '+')
								operacion = Double.parseDouble(cantidad1) + valor;
							else
								operacion = Double.parseDouble(cantidad1) - valor;
							
							nuevaFuncion = nuevaFuncion.replace(cantidad1+funcion.charAt(i)+"X", operacion+"");
							System.out.println("NOP: "+nuevaFuncion); // -----------------------------	
						}
						else if(funcion.charAt(i-1) == 'X')
						{
							cantidad2 = this.capturarNumComp(funcion.charAt(i), nuevaFuncion, "DERECHA");
							System.out.println("cantidad2: "+cantidad2); // -----------------------------
							if(funcion.charAt(i) == '+')
								operacion = valor + Double.parseDouble(cantidad2);
							else
								operacion = valor - Double.parseDouble(cantidad2);
							nuevaFuncion = nuevaFuncion.replace(cantidad2+funcion.charAt(i)+"X", operacion+"");
						}
						else
						{*/
							System.out.println("nueva funcion "+nuevaFuncion); //----------------------
							cantidad1 = this.capturarNumComp(funcion.charAt(i), nuevaFuncion, "IZQUIERDA");
							cantidad2 = this.capturarNumComp(funcion.charAt(i), nuevaFuncion, "DERECHA");
							System.out.println("cantidad1: "+cantidad1);// -----------------------------
							System.out.println("cantidad2 :"+cantidad2);// -----------------------------
							
							if(funcion.charAt(i) == '+')
								operacion = Double.parseDouble(cantidad1) + Double.parseDouble(cantidad2);
							else
								operacion = Double.parseDouble(cantidad1) - Double.parseDouble(cantidad2);
							
							System.out.println("/nuevaFuncion:"+nuevaFuncion); //----------------------
							System.out.println(cantidad1+funcion.charAt(i)+cantidad2); //----------------------
							nuevaFuncion = nuevaFuncion.replace(cantidad1+funcion.charAt(i)+cantidad2, operacion+"");
							System.out.println("//nuevaFuncion"+nuevaFuncion); //----------------------
						//}
					}
				}
			}
			vuelta++;
		}
		return redondearDecimales(Double.parseDouble(nuevaFuncion));
	}
	
	/*
	 * Este método se encarga de redondear todos los decimales que salga a solo el valor de redondeo (4)
	 */
	public double redondearDecimales(double valorInicial) {
        return Math.round(valorInicial * redondeo) / redondeo;
    }
	
	/*
	 * Tabula los signos de Xi y Xm. Que para ello realiza la función y extrae solo el signo
	 */
	public void tabularSignoXiyXm()
	{
		while(getSigno(calcularFuncion(Xi)) == '-' && getSigno(calcularFuncion(Xf)) != '+')
		{
			Xi++;
			Xf++;
		}
	}
	
	public void calcularSignofXi() {
		signofXi = this.getSigno(fXi);
	}
	
	public void calcularfXi()
	{
		fXi = this.calcularFuncion(Xi);
		this.calcularSignofXi();
	}
	
	public void calcularSignofXf() {
		signofXf = this.getSigno(Xf);
	}
	
	public void calcularfXf()
	{
		fXf = this.calcularFuncion(Xf);
		this.calcularSignofXf();
	}
	
	public void calcularSignofXm() {
		signofXm = this.getSigno(fXm);
	}
	
	public void calcularfXm()
	{
		fXm = this.calcularFuncion(Xm);
		this.calcularSignofXm();
	}
	
	public void calcularXm() {
		Xm = this.redondearDecimales((Xi+Xf)/2);
	}
	
	public void calcularXr() {
		Xr = this.redondearDecimales(Xf-((fXf*(Xi-Xf))/(fXi-fXf)));
	}
	
	public void calcularfXr() {
		fXr = this.calcularFuncion(Xr);
		SignofXr = this.getSigno(fXr);
	}
	
	public void calcularEs() {
		Es = 0.5*Math.pow(10, 2-n);
	}
	
	public void calcularEa() {
		if(Xm==0)
			Ea = redondearDecimales(Math.abs((Xr-XAnterior)/Xr)*100);
		else
			Ea = redondearDecimales(Math.abs((Xm-XAnterior)/Xm)*100);
	}
	
	/*
	 * Este método lo que hace es actualizar el XAnterior=Xm
	 * y si el signo de Xm es negativo entonces: Xi=Xm. Positivo: Xf=Xm
	 */
	public void actualizarXmAXiXf()
	{
		XAnterior = Xm;
		if(signofXm == '-')
			Xi = Xm;
		else
			Xf = Xm;
	}
	
	/*
	 * Este método lo que hace es actualizar el XAnterior=Xi
	 * y si el signo de Xr es negativo entonces: Xi=Xr. Positivo: Xf=Xr
	 */
	public void actualizarXrAXiXf()
	{
		XAnterior = Xr;
		if(SignofXr == '-')
			Xi = Xr;
		else
			Xf = Xr;
	}
	
	// --- SET's --- //
	public void setXi(double Xi) {
		this.Xi = Xi;
	}
	
	public void setXf(double Xf) {
		this.Xf = Xf;
	}
	
	public void setXAnterior(double XMA) {
		this.XAnterior = XMA;
	}
	
	// --- GET's --- //
	public char getSigno(double num)
	{
		if(num >= 0)
			return '+';
		else
			return '-';
	}
	
	public double getEs() {
		return Es;
	}
	
	public double getEa() {
		return Ea;
	}
	
	public double getXi() {
		return Xi;
	}
	
	public double getXf() {
		return Xf;
	}
	
	/*
	 * Retorna los primero valores para la tabla del metodo Bisección
	 */
	public Object[][] getFirstDatosBis(int i) {
		Object[][] datos = {{new Integer(1), new Double(Xi), new Character(signofXi), new Double(Xf), new Character(signofXf), new Double(Xm), new Character(signofXm), "No hay"}};
		return datos;
	}
	
	/*
	 * Retorna los primero valores para la tabla del metodo Regla Falsa
	 */
	public Object[][] getFirstDatosRF(int i) {
		Object[][] datos = {{new Integer(1), new Double(Xi), new Double(fXi), new Double(Xf), new Double(fXf), new Double(Xr), new Double(fXr), "No hay"}};
		return datos;
	}
	
	/*
	 * Este método retorna los datos para el método de Bisección
	 */
	public Object[] getDatosBis(int i) {
		Object[] datos = {i, Xi, signofXi, Xf, signofXf, Xm, signofXm, Ea+"%"};
		return datos;
	}
	
	/*
	 * Este método retorna los datos para el metodo de Regla Falsa (RF)
	 */
	public Object[] getDatosRF(int i) {
		Object[] datos = {i, Xi, fXi, Xf, fXf, Xr, fXr, Ea+"%"};
		return datos;
	}
}
