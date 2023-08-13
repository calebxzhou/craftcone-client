package calebxzhou.craftcone.ui

/**
 * Created  on 2023-04-09,10:34.
 */
data class ConeTheme(
    //背景颜色
    val bgColor: ConeColor,
    //字体颜色（启用/停用）
    val fontActiveColor: ConeColor,
    val fontInactiveColor: ConeColor,
    //组件外框颜色
    val widgetOutlineColor: ConeColor,
    //滑块颜色
    val sliderHandleColor: ConeColor,
    //提示框背景色
    val tooltipBgColor: ConeColor,
    //提示框外框色
    val tooltipOutlineColor: ConeColor
){
    companion object{
        var now: ConeTheme = ConeTheme(
            DefaultColors.PineGreen.color,
            DefaultColors.White.color,
            DefaultColors.Gray.color,
            DefaultColors.KleinBlue.color,
            DefaultColors.LightBlue.color,
            DefaultColors.OliveGreen.color,
            DefaultColors.LightBlue.color

            )
    }
}
