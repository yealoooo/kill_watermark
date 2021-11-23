package cn.video.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@TableName(value = "parse_log")
public class ParseLogEntity {

	@TableId(value = "id", type = IdType.AUTO)
	private Long id;

	@TableField(value = "request_data")
	private String requestData;

	@TableField(value = "api")
	private String api;

	@TableField(value = "parse_data")
	private String parseData;

	@TableField(value = "use_time")
	private Long useTime;

	@TableField(value = "request_date")
	private java.sql.Timestamp requestDate;

	@TableField(value = "response_message")
	private String responseMessage;

	@TableField(value = "response_code")
	private Long responseCode;


}
