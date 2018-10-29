package com.abhsy.easy.constant;

public class ErrorCode {

	/**
	 * 返回状态码，成功
	 */
	public static final int SUCESS = 0;

	/**
	 * 没有授权
	 */
	public static final int AUTH_ERROR = 101;

	/**
	 * 数字签名校验失败
	 */
	public static final int SIGN_ERROR = 102;

	/**
	 * 重复请求
	 */
	public static final int REQUEST_REPEAT_ERROR = 103;

	/**
	 * 请求过期，当前请求时间与系统时间相差超过10分钟
	 */
	public static final int REQUEST_OVERTIME_ERROR = 104;

	/**
	 * 当前上报方报文cid与秘钥不匹配
	 */
	public static final int MATCHING_ERROR = 105;

	/**
	 * 数据解析失败
	 */
	public static final int PARSE_ERROR = 201;

	/**
	 * 报文解密失败
	 */
	public static final int DECRYPT_ERROR = 202;

	/**
	 * 服务器错误
	 */
	public static final int INNER_ERROR = 500;

	/**
	 * 上传文件错误
	 */
	public static final int UPLOAD_ERROR = 501;

	/**
	 * 操作数据库失败
	 */
	public static final int BG_DATABASE_ERROR = 502;

	/**
	 * 获取数据失败
	 */
	public static final int BG_GETDATA_ERROR = 503;

	/**
	 * 数据校验错误
	 */
	public static final int VALIDATE_ERROR = 504;

	/**
	 * excel解析报错
	 */
	public static final int EXCEL_ERROR = 505;

}
