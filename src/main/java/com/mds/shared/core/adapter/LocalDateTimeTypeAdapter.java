package com.mds.shared.core.adapter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.time.LocalDateTime;

/**
 * Gson type adapter for {@link LocalDateTime} serialization and deserialization.
 *
 * <p>Serializes a {@link LocalDateTime} into a nested JSON structure with
 * separate {@code date} and {@code time} objects, and deserializes the same
 * structure back.
 *
 * @author MDS
 * @since 0.0.1-SNAPSHOT
 */
public class LocalDateTimeTypeAdapter implements JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {

  public JsonElement serialize(LocalDateTime dateTime, Type type, JsonSerializationContext context) {
    JsonObject jsonObject = new JsonObject();

    JsonObject dateObject = new JsonObject();
    dateObject.addProperty("year", dateTime.getYear());
    dateObject.addProperty("month", dateTime.getMonthValue());
    dateObject.addProperty("day", dateTime.getDayOfMonth());
    jsonObject.add("date", dateObject);

    JsonObject timeObject = new JsonObject();
    timeObject.addProperty("hour", dateTime.getHour());
    timeObject.addProperty("minute", dateTime.getMinute());
    timeObject.addProperty("second", dateTime.getSecond());
    timeObject.addProperty("nano", dateTime.getNano());
    jsonObject.add("time", timeObject);

    return jsonObject;
  }

  @Override
  public LocalDateTime deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
    JsonObject jsonObject = json.getAsJsonObject();
    JsonObject dateObject = jsonObject.get("date").getAsJsonObject();
    int year = dateObject.get("year").getAsInt();
    int month = dateObject.get("month").getAsInt();
    int day = dateObject.get("day").getAsInt();

    JsonObject timeObject = jsonObject.get("time").getAsJsonObject();
    int hour = timeObject.get("hour").getAsInt();
    int minute = timeObject.get("minute").getAsInt();
    int second = timeObject.get("second").getAsInt();
    int nano = timeObject.get("nano").getAsInt();

    return LocalDateTime.of(year, month, day, hour, minute, second, nano);
  }
}
