class ChartController extends JMount{
    constructor(){
        super().mount_()
    }

    _init(){
        this.actionSet = new ActionSet("charts")
        this.viewInitialize = this.actionSet.viewInitialize
        this.viewContent = E.div()
        this.viewInitialize.setViewContent(this.viewContent)
        this.chartConfiguration()
        this.chartConfiguration('line')
        this.chartConfiguration('pie')
        this.chartConfiguration('doughnut')
        this.chartConfiguration('radar')
        this.chartConfiguration('polarArea')
        this.additionalInfo()

    }

    chartConfiguration(type = 'bar'){
        let chartData = extractOlympicsData(["Brazil", "Argentina", "United States of America", "Germany"], "Gold");
        chartData.title = "Gold medals - Tokyo 2020 Olympics";
        chartData.type = type;
        chartData.backgroundColor = ["green", "blue", "yellow", "red"];
        let viewChart = new ViewChart(chartData);
        this.viewContent.a(viewChart, E.br())
    }

    additionalInfo(){
        let divAditionalInfo = E.div().c('additional_info').a(
            E.h3().t("Additional Information"),
            E.p().t("The data used in this example is based on the Tokyo 2020 Olympics medal count. The chart displays the number of gold medals won by selected countries."),
            E.p().t("You can change the chart type by modifying the 'type' parameter in the 'chartConfiguration' method. Available types include 'bar', 'line', 'pie', 'doughnut', 'radar', and 'polarArea'."),
            E.p().t("As you can see, Kalida is quite versatile when integrating third-party libraries, such as Chart.js, used in the example shown.")
        );
        this.viewContent.a(divAditionalInfo)
    }



}