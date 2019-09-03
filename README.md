# Belatrix Android

Prueba Android Belatrix

## Pregunta 1

### Pregunta:
Propón los pasos, clases, layouts y recursos que utilizarías para hacer un Stepper reutilizable y que cumpla con los parámetros definidos en la guía de material design:

### Respuesta:
1. Crear layout base donde contendrá el item de la vista. Esta contendrá lo siguiente:
    
    * Imagen con forma de circulo que tendrá las dimensiones de 24x24dp.
    * Linea vertical que se ajustará al tamaño de la pantalla.
    * Textview con el titulo
    * Textview con la descripción.
    * Crear dos botones en la parte inferior. Uno de positivo y otro de cancelar.
    
2. Definir un recurso attr con los siguientes elementos:

    * Orden: Indicará la posición a visualizarse la pantalla.
    * Titulo: Titulo a mostrar
    * Descripción: Descripción a mostrar
    * Mensaje_realizado: Mensaje cuando haya realizado el item. Esto sustituirá a descripción.
    * Estado: Saber el estado del item (normal, seleccionado o realizado).
    * item_color_normal: Color normal del item.
    * item_color_activado: Color activado del item.
    * icono_realizado: Icono cuando se haya realizado el item.
    * item_color_linea: Color de la linea vertical
    * item_color_error: Color de error.
    
3. Crear clase Customizado que herede de FrameLayout.

    * Está recibirá los recursos definido del attr.
    * Animación correspondiente dependiendo del estado y boton seleccionado.
    * Crear los metodos para para los cambios de estado a nivel de codigo.
    * Crear los metodos para los cambios de textos, color e imagen a nivel de codigo.
    
4. Se implementará desde un layout cualquiera, donde se utilizará la vista personalizada creada anteriormente y dentro de ella se podrá agregar el contenido para ese item. 
            


## Pregunta 2

### Pregunta
Crea un shake action en android y pon el código

### Respuesta:
Revisar el módulo de shake_action con la respuesta.

### Codigo ShakeDetector

```kotlin
class ShakeDetector: SensorEventListener {

    private val SHAKE_THRESHOLD_GRAVITY = 2.7f
    private val SHAKE_SLOP_TIME_MS = 500
    private val SHAKE_COUNT_RESET_TIME_MS = 3000

    private var mListener: OnShakeListener? = null
    private var mShakeTimestamp: Long = 0
    private var mShakeCount: Int = 0



    fun setOnShakeListener(listener: OnShakeListener) {
        this.mListener = listener
    }

    interface OnShakeListener {
        fun onShake(count: Int)
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
    }

    override fun onSensorChanged(event: SensorEvent) {

        if (mListener != null) {
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]

            val gX = x / SensorManager.GRAVITY_EARTH
            val gY = y / SensorManager.GRAVITY_EARTH
            val gZ = z / SensorManager.GRAVITY_EARTH

            val gForce = Math.sqrt((gX * gX + gY * gY + gZ * gZ).toDouble()).toFloat()
            if (gForce > SHAKE_THRESHOLD_GRAVITY) {
                val now = System.currentTimeMillis()
                if (mShakeTimestamp + SHAKE_SLOP_TIME_MS > now) {
                    return
                }

                if (mShakeTimestamp + SHAKE_COUNT_RESET_TIME_MS < now) {
                    mShakeCount = 0
                }

                mShakeTimestamp = now
                mShakeCount++

                mListener!!.onShake(mShakeCount)
            }
        }
    }

}
```

### Codigo MainActivity

```kotlin

class MainActivity : AppCompatActivity(),  ShakeDetector.OnShakeListener {



    private var mSensorManager: SensorManager? = null
    private var shakeDetector = ShakeDetector();


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        shakeDetector.setOnShakeListener(this)
        mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager?;
        mSensorManager!!.registerListener(shakeDetector, mSensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }



    override fun onResume() {
        super.onResume()
        mSensorManager!!.registerListener(shakeDetector, mSensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }


    override fun onShake(count: Int) {
     Toast.makeText(this, "onShake: $count", Toast.LENGTH_LONG).show()
    }
}

```

## Pregunta 3

### Pregunta
Explica cómo organizas en base a tu experiencia un proyecto en Android utilizando MVP e implementando Clean Architecture, menciona los paquetes que utilizarías y la distribución de módulos.


### Respuesta:

Lo divido en 3 módulos:
 
1. Presentation:
    * Actividad, fragment, service: Vista que se conecta al presenter.
    * View: Interface que comunica actividad, fragment o service con el Presenter.
    * Presenter: Clase que realiza las ejecuciones de los UseCase que necesita la vista.
    * Entities: Entidades del módulo presentation.
    * Mapper: Mapper que convierte las entidades del domain por el presentation y viceversa.

NOTA: Dependiendo del proyecto y para modular el proyecto las actividades lo divido por modulos para que sean independientes.

2. Domain:
    * UseCase: Caso de uso que realiza un función del Repository.
    * Repository: Interface que contiene los metodos a usar por el UseCase.
    * Entities: Entidades del módulo domain.
    
3. Data: 

    * RepositoryImpl: Clase que implementa a Repository del modulo domain.
    * DataStoreFactory: Clase factory que se usa para especificar si se usa la implemetación de remote o local.
    * RemoteDataStore: Clase que contiene la implementación de los metodos de Remote (Consumo de servicio)*[]:
        * Remote: Interface que contiene los metodos para el consumo de servicio.
        * RemoteImpl: Clase que implementa de Remote que realiza el consumo de servicio en si.
    * LocalDataStore: Clase que contiene la implementación de los metodos de Local (base de datos).
        * Local: Interface  que contiene los metodos para las consultas de BD.
        * LocalImpl: Clas que implementa de Local que realiza las consultas de BD.
    * Entities: Entidades del módulo data.    
    * Mapper: Mapper que convierte las entidades del domain por el data y viceversa.

NOTA: Dependiendo del proyecto y para modular el proyecto divido la capa de data en data, remote (consumo de servicios) y local (base de datos)

## Pregunta 4
### Pregunta
Diseña un custom view de una brújula utilizando canvas y pon el código que utilizarías en esta sección.


```kotlin
class CompassView : View {

    private var direction: Float = 0.toFloat()

    constructor(context: Context) : super(context) {
        // TODO Auto-generated constructor stub
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        // TODO Auto-generated constructor stub
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        // TODO Auto-generated constructor stub
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(
            View.MeasureSpec.getSize(widthMeasureSpec),
            View.MeasureSpec.getSize(heightMeasureSpec)
        )
    }

    override fun onDraw(canvas: Canvas) {

        val w = measuredWidth
        val h = measuredHeight
        val r: Int
        if (w > h) {
            r = h / 2
        } else {
            r = w / 2
        }

        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 5f
        paint.color = Color.BLACK

        canvas.drawCircle((w / 2).toFloat(), (h / 2).toFloat(), r.toFloat(), paint)

        paint.color = Color.RED
        canvas.drawLine(
            (w / 2).toFloat(),
            (h / 2).toFloat(),
            (w / 2 + r * Math.sin((-direction).toDouble())).toFloat(),
            (h / 2 - r * Math.cos((-direction).toDouble())).toFloat(),
            paint
        )

    }

    fun update(dir: Float) {
        direction = dir
        invalidate()
    }
}```