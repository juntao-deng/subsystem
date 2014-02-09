package net.juniper.jmp.tracer.uid;
/**
 * 
 * @author juntaod
 *
 */
public class OidBaseAlgorithm {
	/** MIN CODE 33 */
	private final static byte MIN_CODE = 48;

	/** MAX CODE 90 */
	private final static byte MAX_CODE = 90;

	/**
	 * OID LENGTH
	 */
	private final int CODE_LENGTH = 14;

	/** current oid base */
	private byte[] oidBaseCodes = new byte[CODE_LENGTH];

	private static OidBaseAlgorithm instance = new OidBaseAlgorithm();
	
	private OidBaseAlgorithm() {
	}

	public void setOidBaseCodes(byte[] oidBaseCodes) {
		if (oidBaseCodes.length != CODE_LENGTH)
			return;
		System.arraycopy(oidBaseCodes, 0, this.oidBaseCodes, 0, CODE_LENGTH);
	}

	public static OidBaseAlgorithm getInstance() {
		return instance;
	}

	/**
	 * get next oid
	 * @return
	 */
	public String nextOidBase() {
		for (int i = oidBaseCodes.length - 1; i >= 0; --i) {
			byte code = (byte) (oidBaseCodes[i] + 1);
			boolean carryUp = false;
			byte newCode = code;
			if (code > MAX_CODE) {
				newCode = MIN_CODE;
				carryUp = true;
			}
			if (newCode == 58) {
				newCode = 65;
			}
				oidBaseCodes[i] = newCode;
			
			if (!carryUp)
				break;
		}

		return new String(oidBaseCodes);
	}
}