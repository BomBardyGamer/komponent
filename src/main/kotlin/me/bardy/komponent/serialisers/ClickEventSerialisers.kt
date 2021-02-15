package me.bardy.komponent.serialisers

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.encoding.encodeStructure
import me.bardy.komponent.event.ClickEvent
import me.bardy.komponent.event.ClickEvent.*

/**
 * Serialisers for [ClickEvent]s
 *
 * @author Callum Seabrook
 */
sealed class ClickEventSerialiser<in T : ClickEvent>(eventName: String, private val keyName: String) : SerializationStrategy<T> {

    override val descriptor = buildClassSerialDescriptor(eventName) {
        element<String>(keyName)
    }

    override fun serialize(encoder: Encoder, value: T) = encoder.encodeStructure(descriptor) {
        encodeStringElement(descriptor, 0, value.content)
    }
}

object OpenURLSerialiser : ClickEventSerialiser<OpenURL>("OpenURL", "open_url"), KSerializer<OpenURL> {

    override fun deserialize(decoder: Decoder): OpenURL = decoder.decodeStructure(descriptor) {
        OpenURL(decodeStringElement(descriptor, 0))
    }
}

object RunCommandSerialiser : ClickEventSerialiser<RunCommand>("RunCommand" ,"run_command"), KSerializer<RunCommand> {

    override fun deserialize(decoder: Decoder): RunCommand = decoder.decodeStructure(descriptor) {
        RunCommand(decodeStringElement(descriptor, 0))
    }
}

object SuggestCommandSerialiser : ClickEventSerialiser<SuggestCommand>("SuggestCommand", "suggest_command"), KSerializer<SuggestCommand> {

    override fun deserialize(decoder: Decoder): SuggestCommand = decoder.decodeStructure(descriptor) {
        SuggestCommand(decodeStringElement(descriptor, 0))
    }
}

object ChangePageSerialiser : ClickEventSerialiser<ChangePage>("ChangePage", "change_page"), KSerializer<ChangePage> {

    override fun deserialize(decoder: Decoder): ChangePage = decoder.decodeStructure(descriptor) {
        ChangePage(decodeStringElement(descriptor, 0))
    }
}

object CopyToClipboardSerialiser : ClickEventSerialiser<CopyToClipboard>("CopyToClipboard", "copy_to_clipboard"), KSerializer<CopyToClipboard> {

    override fun deserialize(decoder: Decoder): CopyToClipboard = decoder.decodeStructure(descriptor) {
        CopyToClipboard(decodeStringElement(descriptor, 0))
    }
}