class ViewChart extends JView{
    constructor(entity){
        super(entity).mount_()
    }

    _init(){

        A.div().a(
            A.h2("chartType").t(`${this.type} chart`),
            A.canvas('chart')
        );
        this._applyChart()
    }

    _applyChart(){
        this.context = this.$chart.getContext('2d');
        this.chart = new Chart(this.context, {
            type: this.type, // Tipo de gráfico: barras
            data: {
                labels: this.labels, // Legendas
                datasets: [{
                    label: this.title, // Nome do conjunto de dados
                    data: this.data, // Os números do gráfico
                    backgroundColor: this.backgroundColor // Cores das barras
                }]
            }
        });
    }

}