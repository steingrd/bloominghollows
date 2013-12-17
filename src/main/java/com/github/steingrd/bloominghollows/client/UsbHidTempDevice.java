package com.github.steingrd.bloominghollows.client;

import java.io.IOException;

import com.codeminders.hidapi.ClassPathLibraryLoader;
import com.codeminders.hidapi.HIDDevice;
import com.codeminders.hidapi.HIDManager;

public class UsbHidTempDevice implements TempDevice {

	private static final int VENDOR_ID = 3141;
    private static final int PRODUCT_ID = 29697;
    private static final int BUFSIZE = 2048;
	
    public UsbHidTempDevice() {
    	ClassPathLibraryLoader.loadNativeHIDLibrary();
	}
    
	@Override
	public float getTemperature() {
		HIDDevice dev;
		try {
			HIDManager hidManager = HIDManager.getInstance();
			dev = hidManager.openById(VENDOR_ID, PRODUCT_ID, null);

			byte[] temp = new byte[] { 
					(byte) 0x01, (byte) 0x80, (byte) 0x33,
					(byte) 0x01, (byte) 0x00, (byte) 0x00, 
					(byte) 0x00, (byte) 0x00 
			};

			dev.write(temp);

			try {
				byte[] buf = new byte[BUFSIZE];
				dev.read(buf);

				int rawtemp = (buf[3] & (byte) 0xFF) + (buf[2] << 8);
				if ((buf[2] & 0x80) != 0) {
					/* return the negative of magnitude of the temperature */
					rawtemp = -((rawtemp ^ 0xffff) + 1);
				}

				return c2u(rawToC(rawtemp), 'C');

			} finally {
				dev.close();
				hidManager.release();
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	static float rawToC(int rawtemp)
    {
        float temp_c = rawtemp * (125.f / 32000.f);
        return temp_c;
    }
    
    static float c2u(float degC, char unit)
    {
        if (unit == 'F')
            return (degC * 1.8f) + 32.f;
        else if (unit == 'K')
            return (degC + 273.15f);
        else
            return degC;
    }

}
