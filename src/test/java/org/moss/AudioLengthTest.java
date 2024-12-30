package org.moss;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.moss.AudioLength.getAudioLength;

class AudioLengthTest {

    @Test
    void getAudioLengthTest() throws Exception {
        assertEquals(102381, getAudioLength("C:/Users/jacks/OneDrive/Documents/Image-Line/FL Studio/Projects/rewindVeeze/rewindVeeze.wav"));
    }
}