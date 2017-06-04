package com.example.myapplication.samples.dashboard;

import java.util.ArrayList;
import java.util.List;

import com.example.myapplication.samples.backend.DataService;
import com.example.myapplication.samples.backend.data.Product;
import com.example.myapplication.samples.backend.mock.MockDataGenerator;
import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.Credits;
import com.vaadin.addon.charts.model.ListSeries;
import com.vaadin.addon.charts.model.PlotOptionsBar;
import com.vaadin.addon.charts.model.Series;

@SuppressWarnings("serial")
public class TopPriceProductChart extends Chart {

    public TopPriceProductChart() {
        setCaption("Top Grossing Products");
        getConfiguration().setTitle("");
        getConfiguration().getChart().setType(ChartType.BAR);
        getConfiguration().getChart().setAnimation(false);
        getConfiguration().getxAxis().getLabels().setEnabled(false);
        getConfiguration().getxAxis().setTickWidth(0);
        getConfiguration().getyAxis().setTitle("");
        setSizeFull();

        List<Product> Products = new ArrayList<Product>(DataService.get()
                .getAllProducts());

        List<Series> series = new ArrayList<Series>();
        for (int i = 0; i < 6; i++) {
            Product Product = Products.get(i);
            PlotOptionsBar opts = new PlotOptionsBar();
            opts.setColor(MockDataGenerator.chartColors[5 - i]);
            opts.setBorderWidth(0);
            opts.setShadow(false);
            opts.setPointPadding(0.4);
            opts.setAnimation(false);
            ListSeries item = new ListSeries(Product.getProductName(), Product.getPrice());
            item.setPlotOptions(opts);
            series.add(item);

        }
        getConfiguration().setSeries(series);

        Credits c = new Credits("");
        getConfiguration().setCredits(c);

        PlotOptionsBar opts = new PlotOptionsBar();
        opts.setGroupPadding(0);
        getConfiguration().setPlotOptions(opts);

    }
}
