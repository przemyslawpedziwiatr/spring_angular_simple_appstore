package nativeapp.utilities;

import sun.misc.BASE64Decoder;

import java.io.ByteArrayInputStream;

/**
 * Created by panpr on 06.12.2017.
 */
public final class B64Converter {
    private B64Converter() {

    }

    public static ByteArrayInputStream convertFile(String stringData, int substringStart) throws Exception {
        BASE64Decoder base64Decoder = new BASE64Decoder();
        String baseCode = stringData.substring(substringStart);
        ByteArrayInputStream convertedFile = new ByteArrayInputStream(
                base64Decoder.decodeBuffer(
                        baseCode
                )
        );
        return convertedFile;
    }

}
