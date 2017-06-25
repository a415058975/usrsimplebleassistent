// Generated code from Butter Knife. Do not modify!
package com.usr.usrsimplebleassistent;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class ServicesActivity$$ViewBinder<T extends com.usr.usrsimplebleassistent.ServicesActivity> extends com.usr.usrsimplebleassistent.MyBaseActivity$$ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    super.bind(finder, target, source);

    View view;
    view = finder.findRequiredView(source, 2131230840, "field 'rlTop'");
    target.rlTop = finder.castView(view, 2131230840, "field 'rlTop'");
    view = finder.findRequiredView(source, 2131230898, "field 'filterView'");
    target.filterView = view;
    view = finder.findRequiredView(source, 2131230900, "field 'viewShadow'");
    target.viewShadow = view;
    view = finder.findRequiredView(source, 2131230807, "field 'lvServices'");
    target.lvServices = finder.castView(view, 2131230807, "field 'lvServices'");
    view = finder.findRequiredView(source, 2131230793, "field 'ivBle'");
    target.ivBle = finder.castView(view, 2131230793, "field 'ivBle'");
    view = finder.findRequiredView(source, 2131230893, "field 'tvServiceName'");
    target.tvServiceName = finder.castView(view, 2131230893, "field 'tvServiceName'");
    view = finder.findRequiredView(source, 2131230892, "field 'tvServiceMac'");
    target.tvServiceMac = finder.castView(view, 2131230892, "field 'tvServiceMac'");
    view = finder.findRequiredView(source, 2131230891, "field 'tvServiceCount'");
    target.tvServiceCount = finder.castView(view, 2131230891, "field 'tvServiceCount'");
  }

  @Override public void unbind(T target) {
    super.unbind(target);

    target.rlTop = null;
    target.filterView = null;
    target.viewShadow = null;
    target.lvServices = null;
    target.ivBle = null;
    target.tvServiceName = null;
    target.tvServiceMac = null;
    target.tvServiceCount = null;
  }
}
