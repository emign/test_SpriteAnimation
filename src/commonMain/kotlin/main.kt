import com.soywiz.korev.*
import com.soywiz.korge.view.*
import com.soywiz.korim.format.*
import com.soywiz.korio.file.std.*
import me.emig.engineEmi.*
import me.emig.engineEmi.screenElements.canvasElements.*


/**
 * Das Default (und eigentlich immer) das einzige Engine-Objekt
 */
val engine = Engine()

/**
 * Startpunkt für alle Programme.
 * Hier werden u.a. [Ebody] und [CanvasElement] Objekte bei der [Engine] registriert.
 * Es gibt drei Bereiche:
 * init : Dieser Code-Block wird zur Konfiguration der Engine verwendet. Hier kann man z.B. die Höhe und Breite des Fensters festlegen.
 * Wenn man diesen Block leer lässt, werden Standard-Werte geladen
 * viewWillLoad: Dieser Code-Block wird NACH der Konfiguration aber VOR dem Aufbau des Views (der Anzeige) ausgeführt. Hier sollte man
 * seine Objekte bei der Engine registrieren
 * viewDidLoad: Dieser Code-Block wird NACH dem der View komplett aufgebaut wurde ausgeführt. Hier sollte man Code platzieren, der darauf
 * angewiesen ist, dass Objekte bereits fertig erstellt und registriert wurden. Dies trifft vor allem auf [Ebody] Objekte zu.
 */
suspend fun main() {
	engine.run {

		/**
		 * Code um die Engine zu konfigurieren.
		 */
		init {

		}

		/**
		 * Code der VOR dem Aufbau des Views ausgeführt wird
		 */
		viewWillLoad {
			val bitmap = resourcesVfs["gfx/character.png"].readBitmap()


			val spriteView = SpriteView()
			spriteView.scale = 3.0
			val animationDown = SpriteAnimation(
					x = 100,
					y = 100,
					columns = 4,
					marginTop = 0,
					marginLeft = 1,
					lines = 1,
					spriteHeight = 32,
					spriteWidth = 16,
					bitmap = bitmap,
					spriteView = spriteView,
					skalierung = 3.0
			)

			val animationRight = SpriteAnimation(
					x = 100,
					y = 100,
					columns = 4,
					lines = 1,
					marginTop = 32,
					marginLeft = 1,
					spriteWidth = 16,
					spriteHeight = 32,
					bitmap = bitmap,
					spriteView = spriteView,
					skalierung = 3.0
			)

			val animationUp = SpriteAnimation(
					x = 100,
					y = 100,
					columns = 4,
					lines = 1,
					marginTop = 64,
					marginLeft = 1,
					spriteWidth = 16,
					spriteHeight = 32,
					bitmap = bitmap,
					spriteView = spriteView,
					skalierung = 3.0
			)

			val animationLeft = SpriteAnimation(
					x = 100,
					y = 100,
					columns = 4,
					lines = 1,
					marginTop = 96,
					marginLeft = 1,
					spriteWidth = 16,
					spriteHeight = 32,
					bitmap = bitmap,
					spriteView = spriteView,
					skalierung = 3.0
			)


			val animationController = AnimationController()

			animationController.addAnimation("left", animationLeft)
			animationController.addAnimation("right", animationRight)
			animationController.addAnimation("up", animationUp)
			animationController.addAnimation("down", animationDown)

			engine.register(spriteView)
			engine.register(animationController)

			val characterSteuerung = Steuerung(animationController, spriteView)
			engine.register(characterSteuerung)
		}

		/**
		 * Code, der NACH dem Aufbau des Views ausgeführt wird
		 */
		viewDidLoad {

		}

		start()
	}
}

class Steuerung(val forAnimation : AnimationController, val view : SpriteView) : Controller {
	override fun reactToKeyEvent(event: KeyEvent) {
		when(event.key){
			Key.RIGHT -> {forAnimation.play("right"); view.x+=5}
			Key.LEFT -> {forAnimation.play("left"); view.x-=5 }
			Key.UP -> {forAnimation.play("up"); view.y-=5 }
			Key.DOWN -> {forAnimation.play("down"); view.y+=5 }
		}
	}
}