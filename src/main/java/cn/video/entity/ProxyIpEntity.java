package cn.video.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@TableName(value = "proxy_ip")
public class ProxyIpEntity {

	@TableId(value = "id")
	private Long id;

	@TableField(value = "ip")
	private String ip;

	@TableField(value = "port")
	private Integer port;


}
