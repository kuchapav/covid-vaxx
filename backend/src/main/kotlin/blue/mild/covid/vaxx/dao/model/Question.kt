package blue.mild.covid.vaxx.dao.model

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.`java-time`.timestamp

object Question : Table("questions") {
    /**
     * Primary key.
     */
    val id = entityId("id")

    /**
     * When this record was created.
     */
    val created = timestamp("created")

    /**
     * When this record was updated. By default same value as [created].
     */
    val updated = timestamp("updated")

    /**
     * Placeholder for frontend.
     */
    val placeholder = varchar("placeholder", DatabaseTypeLength.DEFAULT_STRING)

    /**
     * Czech translation.
     */
    val cs = text("cs")

    /**
     * English translation.
     */
    val eng = text("eng")

    override val primaryKey = PrimaryKey(id)
}
