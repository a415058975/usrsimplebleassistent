// Generated code from Butter Knife. Do not modify!
package com.usr.usrsimplebleassistent;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class GattDetailActivity$$ViewBinder<T extends com.usr.usrsimplebleassistent.GattDetailActivity> extends com.usr.usrsimplebleassistent.MyBaseActivity$$ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    super.bind(finder, target, source);

    View view;
    view = finder.findRequiredView(source, 2131230750, "field 'btnOptions' and method 'onOptionsClick'");
    target.btnOptions = finder.castView(view, 2131230750, "field 'btnOptions'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onOptionsClick();
        }
      });
    view = finder.findRequiredView(source, 2131230748, "field 'btnOption' and method 'onOptionClick'");
    target.btnOption = finder.castView(view, 2131230748, "field 'btnOption'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onOptionClick();
        }
      });
    view = finder.findRequiredView(source, 2131230829, "field 'rvMsg'");
    target.rvMsg = finder.castView(view, 2131230829, "field 'rvMsg'");
    view = finder.findRequiredView(source, 2131230923, "field 'tvProperties'");
    target.tvProperties = finder.castView(view, 2131230923, "field 'tvProperties'");
    view = finder.findRequiredView(source, 2131230866, "field 'rlWrite'");
    target.rlWrite = finder.castView(view, 2131230866, "field 'rlWrite'");
    view = finder.findRequiredView(source, 2131230863, "field 'rlContent'");
    target.rlContent = finder.castView(view, 2131230863, "field 'rlContent'");
    view = finder.findRequiredView(source, 2131230862, "field 'rlBottom'");
    target.rlBottom = finder.castView(view, 2131230862, "field 'rlBottom'");
    view = finder.findRequiredView(source, 2131230936, "field 'bottomShadow'");
    target.bottomShadow = view;
    view = finder.findRequiredView(source, 2131230940, "field 'topShadow'");
    target.topShadow = view;
    view = finder.findRequiredView(source, 2131230937, "field 'filterView'");
    target.filterView = view;
    view = finder.findRequiredView(source, 2131230752, "method 'onSend20Click'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onSend20Click();
        }
      });
    view = finder.findRequiredView(source, 2131230753, "method 'onSend21Click'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onSend21Click();
        }
      });
    view = finder.findRequiredView(source, 2131230754, "method 'onSend22Click'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onSend22Click();
        }
      });
    view = finder.findRequiredView(source, 2131230900, "method 'onstopallClick'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onstopallClick();
        }
      });
  }

  @Override public void unbind(T target) {
    super.unbind(target);

    target.btnOptions = null;
    target.btnOption = null;
    target.rvMsg = null;
    target.tvProperties = null;
    target.rlWrite = null;
    target.rlContent = null;
    target.rlBottom = null;
    target.bottomShadow = null;
    target.topShadow = null;
    target.filterView = null;
  }
}
