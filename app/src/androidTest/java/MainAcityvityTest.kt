import android.content.Context
import android.content.SharedPreferences
import androidx.test.core.app.ApplicationProvider
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import com.example.missingyou.MainActivity

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
}
