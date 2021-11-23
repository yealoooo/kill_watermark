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
@TableName(value = "proposal")
public class ProposalEntity {

	@TableId(value = "id", type = IdType.AUTO)
	private Long id;

	@TableField(value = "proposal_data")
	private String proposalData;


}
