class ViewTokioOlympicOptions extends JView{
    constructor(countries){
        super(countries).mount_()
        this.populateDataList()
        this.manageSelectedCountry()
    }

    _init(){
        A.div().a(
            A.inputText('inputCountry').p('Select a country, and hit enter').a(
                A.datalist('dataList')
            ),
            A.div('divSelectMedalType').a(
                A.label('lblGold').t("Gold").a(
                    A.inputCheckBox('inputGold').v('Gold')
                ),
                A.label('lblSilver').t("Silver").a(
                    A.inputCheckBox('inputSilver').v('Silver')
                ),
                A.label('lblBronze').t("Bronze").a(
                    A.inputCheckBox('inputBronze').v('Bronze')
                ),
                A.label('lblTotal').t("Total").a(
                    A.inputCheckBox('inputTotal').v('Total')
                )
            ),
            A.inputButton('btnReloadCharts').v("Reload Charts"),
            A.div('divSelectedCountries')
        )
    }

    populateDataList(){
        let dataList = this.$dataList;
        dataList.id = "dataListCountries"
        this.$inputCountry.setAttribute('list', 'dataListCountries')
        this.countries.forEach(country => {
            dataList.a(E.option().v(country))
        })

    }

    manageSelectedCountry(){
        let inputs = [this.$inputGold, this.$inputSilver, this.$inputBronze, this.$inputTotal];

        inputs.forEach(input => {
            input.addEventListener('change', () => {
                if(input.checked){
                    inputs.forEach(otherInput => {
                        if(otherInput !== input)
                            otherInput.checked = false;
                    })
                }
            });
        });
    }

    addSelectedCountry(viewRowSelectedCountry){
        this.$divSelectedCountries.a(viewRowSelectedCountry)
    }

}