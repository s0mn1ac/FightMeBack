package apps.somniac.fightme.security.auth;

public class JwtConfig {
	
	public static final String RSA_KEY_PRIVATE = "-----BEGIN RSA PRIVATE KEY-----\r\n" + 
			"MIIEogIBAAKCAQEA747fM7amTSGcLR2nkBmIcM8+crF8J0qI4H0sNPNIJC4ro0rw\r\n" + 
			"wL3mbwQqaBoVAX/bp96N0XdDQ2ROPzLgJjfOHXLy677pXt3jC95m/PSv/3GgKEuT\r\n" + 
			"MHD8B3yS616aENB0TfRCBxCwrHS8jcz4dn2v8JmL09BbB6JTC33fGfG91xTo1rkt\r\n" + 
			"LiKByGoWsVQMyBYDw6hLXz2oAtKfh4YJxRgI2yuG7P010gXUXtW3oe8Q+rqnaNtO\r\n" + 
			"JyuGcLQEWjCbeD7+ctcPF++FLbg0v/YDlc2cShLMDkEEes5HYUBqikV4+XNJK5jG\r\n" + 
			"a/9PLxOEz5B2RjVZa4s3HgVrHKmhVvtbunGhMwIDAQABAoIBACQ8LyTKVuNJ1y63\r\n" + 
			"0pWoVwwBPQfqMsZartON+3bS6bGXTkB34Gy3DLj0H1n5v/2nElmU/0yUxpbWAEPs\r\n" + 
			"9RyyyjmIHphsGy6fT22HKXrPpCN2U2xk6hGktWqIfEyf1Zv/e+giry4UZzTev3qP\r\n" + 
			"a3G41zkEcLMMTAielAMj+fAZscZrd1XY/fxRU0o3be8ZBvl/7UeFT6LPGnUThqW8\r\n" + 
			"Jqi+1B3Ji9zsE0owZ99vtQX7C2ZWuy0HFgqz9ZLHwsspnvq32o84TA42DTMS/eoG\r\n" + 
			"eNMWt1NjyD1sweVHg0pO9Ty7xRD/+ETv5iJG40mqn6ub9TqF0NoPFVec/CwXySNU\r\n" + 
			"KhlFihkCgYEA+ERTpuzeKE2gAYvwN/nCCfynDPYXqy3iko+ByVNu/B7TTxS9z87g\r\n" + 
			"137U2pc7KAqYhuzaP83NIV9PQdJDgQkjLIKGeAGsOLLipMA+0unw8QoJxr6+7c/H\r\n" + 
			"g678MekmqKc0dYPKCtX4PihX6buAD9rCqHXL6v2pSQLx6PNVALLVzMUCgYEA9wUZ\r\n" + 
			"8J5ol3h/jHff/pOhjwhM2rlxrEEQ6CLW0OJSIKFxqhtXfMhmtcIIIlZkpOfq7SAW\r\n" + 
			"XA+dCc9twgQ1qrvJVDJM8RlBevyfxR6rXcXGp9aq83XbnUqI7pHm2dXAOR+uBVg2\r\n" + 
			"KKOcXsUj9F/ECmt5AjUmBH8gxI+8zUCs521eBZcCgYALm2Ytz7UzPS3V5Kz+cPbZ\r\n" + 
			"cK5+tKK2phaJs5vlxbjSoL5QXC/jn1BzwsJA1gE2BgjLhhdOFFkazlHuSd5OEhdK\r\n" + 
			"5W9T/QAIRpTCDxrg0X8qRcXfPNvAUo6JYYz46GWeTPLu65Jun0JT5Z72loWOODQR\r\n" + 
			"nDBEdEeN2k1lDrtSpQnJ4QKBgDG6+y/wFEp6XeNftt6/xgTPvPlD4Fi3eLnjWbAQ\r\n" + 
			"AEzFc+a1PnXGurbY615OWlDAyBEZJqc+FXWGJyiqREutJqkmv0rCtOYXueqrdACa\r\n" + 
			"8pWTqDL/hUkzWkH9aaZtXBekAxrcWXrxBWgm9Egch5E5ddE19gNtvKEV9wKzhJvK\r\n" + 
			"7uTdAoGAEJowC2FOUGZL5SoKzck5yeVEXYLwb15l6QNiHH9+qCqSvWMHQ1V75mZw\r\n" + 
			"rdbcAdB98dE26g5swjjh9MX4T9nIbcb6YRwun0XBnAFzLV3SvOze1CWZVvN+Hmmh\r\n" + 
			"RxSv7JU/LmXLmW5GmYwf7ibLtC6QDXZz+25jMcnHUxQh9/WYu14=\r\n" + 
			"-----END RSA PRIVATE KEY-----";
	
	public static final String RSA_KEY_PUBLIC = "-----BEGIN PUBLIC KEY-----\r\n" + 
			"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA747fM7amTSGcLR2nkBmI\r\n" + 
			"cM8+crF8J0qI4H0sNPNIJC4ro0rwwL3mbwQqaBoVAX/bp96N0XdDQ2ROPzLgJjfO\r\n" + 
			"HXLy677pXt3jC95m/PSv/3GgKEuTMHD8B3yS616aENB0TfRCBxCwrHS8jcz4dn2v\r\n" + 
			"8JmL09BbB6JTC33fGfG91xTo1rktLiKByGoWsVQMyBYDw6hLXz2oAtKfh4YJxRgI\r\n" + 
			"2yuG7P010gXUXtW3oe8Q+rqnaNtOJyuGcLQEWjCbeD7+ctcPF++FLbg0v/YDlc2c\r\n" + 
			"ShLMDkEEes5HYUBqikV4+XNJK5jGa/9PLxOEz5B2RjVZa4s3HgVrHKmhVvtbunGh\r\n" + 
			"MwIDAQAB\r\n" + 
			"-----END PUBLIC KEY-----";

}
