package org.lcamel.jsonstreamextractor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

// We assume that the input is a "valid" JSON.

public class JsonExtractor {
    // [ ... {...} ... {...} ... ]
    //   ^
    public static void extractArrayOfObjects(InputStream is, OutputStream os) throws IOException {
        int braces = 0;
        while (true) {
            // between array elements
            while (true) {
                int b = is.read();
                if (b == '{') {
                    os.write(b);
                    braces++;
                    break;
                } else if (b == ']') {
                    return;
                }
            }

            // the array element: an object
            // [ ... {...} ... {...} ... ]
            //        ^
            anObjectElement:
            while (true) {
                // { ... "..." ... "..." }
                //   ^
                while (true) {
                    int b = is.read();
                    if (b == '{') {
                        os.write(b);
                        braces++;
                    } else if (b == '}') {
                        os.write(b);
                        braces--;
                        if (braces == 0) {
                            os.write('\n');
                            break anObjectElement;
                        }
                    } else if (b == ' ' || b == '\n' || b == '\r' || b == '\t') {
                        // just skip them
                    } else if (b == '"') {
                        os.write(b);
                        break;
                    } else {
                        os.write(b);
                    }
                }
                // ... "..." ...
                //      ^
                while (true) {
                    int b = is.read();
                    os.write(b);        // output the whole string as-is

                    if (b == '"') {
                        break;
                    }

                    if (b == '\\') {
                        b = is.read();  // consume the next byte (even for \ u )
                        os.write(b);
                    }
                }
            }
        }

    }
}
