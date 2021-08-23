package cn.video.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParseVO {
    private int downType;

    private ImageVO imageData;

    private VideoVO videoData;

    public static ParseVO video(String videoUrl, String downLoadUrl, boolean downLoadFlag) {
        return new ParseVO(1, null,  new VideoVO(videoUrl, downLoadUrl, downLoadFlag));
    }

    public static ParseVO image(List<String> downLoadUrlList, boolean downLoadFlag) {
        return new ParseVO(2, new ImageVO(downLoadUrlList, downLoadFlag), null);
    }

    public void setImageData(ImageVO imageData) {
        this.imageData = imageData;
        this.downType = 2;
    }

    public void setVideoData(VideoVO videoData) {
        this.videoData = videoData;
        this.downType = 1;
    }
}
