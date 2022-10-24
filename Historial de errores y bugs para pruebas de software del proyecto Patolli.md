
## Cosas Cazarez/Comunicación

* Al romperse la comunicación, no se manejaba bien y no cerraba la comunicación (Seguía ahí, pero rota)

	 * Forma de arreglarlo

## Cosas Patolli

* Al crear un grupo no dejaba configurar el juego, porque no existía uno (no inicializamos uno default)

	* Creamos un juego inicial con valores default

* Al mover un token, la colisión de tokens siempre se cumplía (El if del hasNoToken estaba invertido)
	* Quitamos la inversión (tenían un ! al inicio) de dos condicionales, del HasNoToken y del willTokenColideWithAnother de || a &&