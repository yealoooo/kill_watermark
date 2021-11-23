package cn.video.entity;


import com.baomidou.mybatisplus.annotation.*;
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
