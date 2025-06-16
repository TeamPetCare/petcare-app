import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TokenDataStore(context: Context) {
    private val Context.dataStore by preferencesDataStore("user_prefs")

    private val TOKEN_KEY = stringPreferencesKey("user_token")
    private val NAME_USER = stringPreferencesKey("user_name")
    private val ID_USER = intPreferencesKey("user_id")

    private val dataStore = context.dataStore

    suspend fun saveUserInfo(token: String, name: String, id: Int) {
        dataStore.edit { prefs ->
            prefs[TOKEN_KEY] = token
            prefs[NAME_USER] = name
            prefs[ID_USER] = id
        }
    }

    suspend fun clearUserInfo() {
        dataStore.edit { prefs ->
            prefs.remove(TOKEN_KEY)
            prefs.remove(NAME_USER)
            prefs.remove(ID_USER)
        }
    }

    val getToken: Flow<String?> = dataStore.data.map { it[TOKEN_KEY] }
    val getName: Flow<String?> = dataStore.data.map { it[NAME_USER] }
    val getId: Flow<Int?> = dataStore.data.map { it[ID_USER] }
}
