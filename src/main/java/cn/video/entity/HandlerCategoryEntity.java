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
@TableName(value = "handler_category")
public class HandlerCategoryEntity {

	@TableId(value = "id", type = IdType.AUTO)
	private Long id;

	@TableField(value = "handler_explain")
	private String handlerExplain;

	@TableField(value = "domain")
	private String domain;

	@TableField(value = "handler_bean_name")
	private String handlerBeanName;


}
