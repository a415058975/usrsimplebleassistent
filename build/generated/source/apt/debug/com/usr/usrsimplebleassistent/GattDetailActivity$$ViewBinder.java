// Generated code from Butter Knife. Do not modify!
package com.usr.usrsimplebleassistent;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class GattDetailActivity$$ViewBinder<T extends com.usr.usrsimplebleassistent.GattDetailActivity> extends com.usr.usrsimplebleassistent.MyBaseActivity$$ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    super.bind(finder, target, source);

    View view;
    view = finder.findRequiredView(source, 2131230744, "field 'btnOptions' and method 'onOptionsClick'");
    target.btnOptions = finder.castView(view, 2131230744, "field 'btnOptions'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onOptionsClick();
        }
      });
    view = finder.findRequiredView(source, 2131230742, "field 'btnOption' and method 'onOptionClick'");
    target.btnOption = finder.castView(view, 2131230742, "field 'btnOption'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onOptionClick();
        }
      });
    view = finder.findRequiredView(source, 2131230806, "field 'rvMsg'");
    target.rvMsg = finder.castView(view, 2131230806, "field 'rvMsg'");
    view = finder.findRequiredView(source, 2131230888, "field 'tvProperties'");
    target.tvProperties = finder.castView(view, 2131230888, "field 'tvProperties'");
    view = finder.findRequiredView(source, 2131230841, "field 'rlWrite'");
    target.rlWrite = finder.castView(view, 2131230841, "field 'rlWrite'");
    view = finder.findRequiredView(source, 2131230838, "field 'rlContent'");
    target.rlContent = finder.castView(view, 2131230838, "field 'rlContent'");
    view = finder.findRequiredView(source, 2131230837, "field 'rlBottom'");
    target.rlBottom = finder.castView(view, 2131230837, "field 'rlBottom'");
    view = finder.findRequiredView(source, 2131230897, "field 'bottomShadow'");
    target.bottomShadow = view;
    view = finder.findRequiredView(source, 2131230901, "field 'topShadow'");
    target.topShadow = view;
    view = finder.findRequiredView(source, 2131230898, "field 'filterView'");
    target.filterView = view;
    view = finder.findRequiredView(source, 2131230746, "method 'onSend20Click'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onSend20Click();
        }
      });
    view = finder.findRequiredView(source, 2131230747, "method 'onSend21Click'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onSend21Click();
        }
      });
    view = finder.findRequiredView(source, 2131230748, "method 'onSend22Click'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onSend22Click();
        }
      });
    view = finder.findRequiredView(source, 2131230870, "method 'onstopallClick'");
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
