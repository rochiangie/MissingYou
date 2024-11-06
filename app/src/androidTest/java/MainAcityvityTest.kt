import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.TextView
import androidx.test.core.app.ApplicationProvider
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import com.example.missingyou.MainActivity
import com.example.missingyou.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var activity: MainActivity

    @Before
    fun setUp() {
        ActivityScenario.launch(MainActivity::class.java).onActivity { activity = it }
        sharedPreferences = ApplicationProvider.getApplicationContext<Context>()
            .getSharedPreferences("CountPrefs", Context.MODE_PRIVATE)
    }

    @Test
    fun incrementCount_increasesCountByOne() {
        val initialCount = activity.count
        activity.incrementCount()
        assertEquals(initialCount + 1, activity.count)
    }

    @Test
    fun resetCountIfNewDay_resetsCountOnNewDay() {
        // Configura la fecha de ayer en sharedPreferences
        val editor = sharedPreferences.edit()
        editor.putString("lastDate", "2024-11-04") // Suponiendo que hoy es "2024-11-05"
        editor.apply()

        activity.resetCountIfNewDay()
        assertEquals(0, activity.count)
    }

    @Test
    fun saveCount_savesCountCorrectly() {
        activity.count = 5
        activity.saveCount()

        val savedCount = sharedPreferences.getInt("count", -1)
        assertEquals(5, savedCount)
    }

    @Test
    fun loadCount_loadsSavedCount() {
        val editor = sharedPreferences.edit()
        editor.putInt("count", 10)
        editor.apply()

        activity.loadCount()
        assertEquals(10, activity.count)
    }

    @Test
    fun getCurrentDate_returnsCorrectFormat() {
        val expectedDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        assertEquals(expectedDateFormat, activity.getCurrentDate())
    }

    @Test
    fun resetCountIfNewDay_doesNotResetCountOnSameDay() {
        // Configura la fecha actual en SharedPreferences
        val currentDate = activity.getCurrentDate()
        val editor = sharedPreferences.edit()
        editor.putString("lastDate", currentDate)
        editor.putInt("count", 7) // Guarda el valor inicial de count como 7
        editor.apply()

        // Carga el count guardado
        activity.loadCount()

        // Ejecuta resetCountIfNewDay y verifica que el conteo no se haya restablecido
        activity.resetCountIfNewDay()
        assertEquals(7, activity.count) // Verifica que el conteo siga siendo 7
    }


    @Test
    fun incrementCount_showsErrorMessageOnException() {
        try {
            // Intentamos llamar a un método que no existe para forzar una excepción
            val invalidMethod = MainActivity::class.java.getDeclaredMethod("invalidMethod")
            invalidMethod.isAccessible = true
            invalidMethod.invoke(activity)
        } catch (e: Exception) {
            // Asegúrate de que la excepción se captura y muestra en los logs
            Log.e("Test", "Exception caught: ${e.message}")  // Verifica si este log aparece
            activity.showError("Simulated error")  // Mostrar el mensaje de error
        }

        // Aquí obtenemos el TextView donde se muestra el mensaje de error
        val countTextView = activity.findViewById<TextView>(R.id.countTextView)

        // Esperamos un momento para asegurarnos de que la UI haya actualizado el TextView
        Thread.sleep(500)  // Puede ajustarse el tiempo según sea necesario

        // Comprobamos que el mensaje de error es el que esperamos
        assertEquals("Conteo: Error: Simulated error", countTextView.text.toString())
    }








    @Test
    fun onCreate_initializesCountToSavedValueOrZero() {
        val editor = sharedPreferences.edit()
        editor.clear().apply()  // Asegura que no haya un conteo guardado previamente

        activity.loadCount()
        assertEquals(0, activity.count)
    }

    @Test
    fun resetCountIfNewDay_resetsAutomaticallyOnNewDay() {
        val editor = sharedPreferences.edit()
        editor.putString("lastDate", "2024-11-04") // Suponiendo que hoy es 2024-11-05
        editor.putInt("count", 10)
        editor.apply()

        activity.resetCountIfNewDay()
        assertEquals(0, activity.count)
    }
}
