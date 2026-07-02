class ChartsController extends JMount{
    constructor(){
        super({
            selectedCountries:["Brazil", "Philippines", "United States of America", "Germany"],
            selectedMedalType: "Gold",
            chartTitle: "Gold medals - Tokyo 2020 Olympics",
            associatedColors: ["green", "yellow", "blue", "red"]
        }).mount_()
    }

    _init(){
        this.countries = tokioOlympicsData.data.map(item => item[0]);
        this.actionSet = new ActionSet("charts")
        this.viewInitialize = this.actionSet.viewInitialize
        this.viewTokioOlympicOptions = new ViewTokioOlympicOptions({countries: this.countries})
        this.viewContent = E.div().a(this.viewTokioOlympicOptions)
        this.viewInitialize.setViewContent(this.viewContent)
        this.loadCharts()
        this.additionalInfo()
        this.clearExempleData()
        this.applyDefaultMedalType()
    }

    _events(){
        this.eventSelectCountry()
        this.eventReloadCharts()
        this.eventSelectMedalType()
    }

    chartConfiguration(type = 'bar'){
        let chartData = extractOlympicsData(this.selectedCountries, this.selectedMedalType);
        chartData.title = this.chartTitle;
        chartData.type = type;
        chartData.backgroundColor = this.associatedColors;
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

    loadCharts(){
        this.chartConfiguration()
        this.chartConfiguration('line')
        this.chartConfiguration('pie')
        this.chartConfiguration('doughnut')
        this.chartConfiguration('radar')
        this.chartConfiguration('polarArea')
    }

    eventSelectCountry(){

        this.viewTokioOlympicOptions.$inputCountry.onchange = (event) => {
            let selectedCountry = event.target.value;
            if (this.countries.includes(selectedCountry)) {
                if (!this.selectedCountries.includes(selectedCountry)) {
                    this.selectedCountries.push(selectedCountry);
                    this.associatedColors = []
                    let viewRowSelectedCountry = new ViewRowSelectedCountry({country: selectedCountry});
                    this.viewTokioOlympicOptions.addSelectedCountry(viewRowSelectedCountry);
                    this.eventRemoveSelectedCountry(viewRowSelectedCountry);
                    for (let i = 0; i < this.selectedCountries.length; i++) {
                        this.associatedColors.push(this.randomColor());
                    }
                }
            } else {
                alert("Please select a valid country from the list.");
            }
        };
    }

    eventSelectMedalType(){
        let {$inputGold, $inputSilver, $inputBronze, $inputTotal} = this.viewTokioOlympicOptions;
        [$inputGold, $inputSilver, $inputBronze, $inputTotal].forEach(input => {
            input.addEventListener('change', () => {
                this.selectedMedalType = "";
                this.chartTitle = "";
                if(input.checked){
                    this.selectedMedalType = input.value;
                    this.chartTitle = `${input.value} medals - Tokyo 2020 Olympics`;
                }
            });
        });
    }

    eventRemoveSelectedCountry(viewRowSelectedCountry){
        viewRowSelectedCountry.$iconDelete.onclick = () => {
            let countryToRemove = viewRowSelectedCountry.country;
            this.selectedCountries = this.selectedCountries.filter(country => country !== countryToRemove);
            this.associatedColors = this.associatedColors.filter((_, index) => index !== this.selectedCountries.indexOf(countryToRemove));
            disapend(viewRowSelectedCountry)
        }
    }


    eventReloadCharts(){
        this.viewTokioOlympicOptions.$btnReloadCharts.onclick = () => {
            if(this.selectedCountries.length === 0){
                alert("Please select at least one country to reload the charts.");
                return;
            }

            if(!this.selectedMedalType){
                alert("Please select a medal type to reload the charts.");
                return;
            }

            removeChildren(this.viewContent)
            this.viewContent.a(this.viewTokioOlympicOptions)
            this.loadCharts();
            this.additionalInfo()
        }
    }

    randomColor() {
        const letters = '0123456789ABCDEF';
        let color = '#';
        for (let i = 0; i < 6; i++) {
            color += letters[Math.floor(Math.random() * 16)];
        }
        return color;
    }

    clearExempleData(){
        this.selectedCountries = []
        this.associatedColors = []
    }


    applyDefaultMedalType(){
        let {$inputGold} = this.viewTokioOlympicOptions;
        $inputGold.checked = true;
    }
    

}