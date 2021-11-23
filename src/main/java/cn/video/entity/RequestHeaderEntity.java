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
@TableName(value = "request_header")
public class RequestHeaderEntity {

	@TableId(value = "id", type = IdType.AUTO)
	private Long id;

	@TableField(value = "head")
	private String head;

	@TableField(value = "value")
	private String value;

	@TableField(value = "handler_category_id")
	private Long handlerCategoryId;


}
