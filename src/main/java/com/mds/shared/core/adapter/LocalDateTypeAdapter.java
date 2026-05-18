package com.mds.shared.core.adapter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.time.LocalDate;

/**
 * Gson type adapter for {@link LocalDate} serialization and deserialization.
 *
 * <p>Serializes a {@link LocalDate} into a JSON object with {@code year},
 * {@code month}, and {@code day} properties, and deserializes the same
 * structure back.
 *
 * @author MDS
 * @since 0.0.1-SNAPSHOT
 */
public class LocalDateTypeAdapter implements JsonDeserializer<LocalDate>, JsonSerializer<LocalDate> {

  @Override
  public LocalDate deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
    JsonObject jsonObject = json.getAsJsonObject();
    int year = jsonObject.get("year").getAsInt();
    int month = jsonObject.get("month").getAsInt();
    int day = jsonObject.get("day").getAsInt();

    return LocalDate.of(year, month, day);
  }

  @Override
  public JsonElement serialize(LocalDate localDate, Type type, JsonSerializationContext context) {
    JsonObject jsonObject = new JsonObject();

    jsonObject.addProperty("year", localDate.getYear());
    jsonObject.addProperty("month", localDate.getMonthValue());
    jsonObject.addProperty("day", localDate.getDayOfMonth());

    return jsonObject;
  }
}
