package in.srain.cube.views.ptr;

import in.srain.cube.views.ptr.indicator.PtrIndicator;

/**
 * A single linked list to wrap PtrUIHandler
 */
class PtrUIHandlerHolder implements PtrUIHandler {

    private PtrUIHandler mHeaderHandler;
    private PtrUIHandler mFooterHandler;
    private PtrUIHandlerHolder mNext;

    private boolean containsHeader(PtrUIHandler handler) {
        return mHeaderHandler != null && mHeaderHandler == handler;
    }

    private boolean containsFooter(PtrUIHandler handler) {
        return mFooterHandler != null && mFooterHandler == handler;
    }

    private PtrUIHandlerHolder() {

    }

    public boolean hasHeaderHandler() {
        return mHeaderHandler != null;
    }

    public boolean hasFooterHandler() {
        return mFooterHandler != null;
    }

    private PtrUIHandler getHeaderHandler() {
        return mHeaderHandler;
    }

    public PtrUIHandler getFooterHandler() {
        return mFooterHandler;
    }

    public static void addHeaderHandler(PtrUIHandlerHolder holder, PtrUIHandler handler) {

        if (null == handler) {
            return;
        }
        if (holder == null) {
            return;
        }
        if (null == holder.mHeaderHandler) {
            holder.mHeaderHandler = handler;
            return;
        }

        PtrUIHandlerHolder current = holder;
        for (; ; current = current.mNext) {

            if (current.mNext == null) {
                break;
            }
            // duplicated
            if (current.mHeaderHandler == null) {
                current.mHeaderHandler = handler;
                return;
            }

            if (current.containsHeader(handler)) {
                return;
            }
        }

        PtrUIHandlerHolder newHolder = new PtrUIHandlerHolder();
        newHolder.mHeaderHandler = handler;
        current.mNext = newHolder;
    }


    public static void addFooterHandler(PtrUIHandlerHolder holder, PtrUIHandler handler) {

        if (null == handler) {
            return;
        }
        if (holder == null) {
            return;
        }
        if (null == holder.mFooterHandler) {
            holder.mFooterHandler = handler;
            return;
        }

        PtrUIHandlerHolder current = holder;
        for (; ; current = current.mNext) {

            if (current.mNext == null) {
                break;
            }

            if (current.mFooterHandler == null) {
                current.mFooterHandler = handler;
                return;
            }

            // duplicated
            if (current.containsFooter(handler)) {
                return;
            }
        }

        PtrUIHandlerHolder newHolder = new PtrUIHandlerHolder();
        newHolder.mFooterHandler = handler;
        current.mNext = newHolder;
    }



    public static PtrUIHandlerHolder create() {
        return new PtrUIHandlerHolder();
    }

    public static PtrUIHandlerHolder removeHeaderHandler(PtrUIHandlerHolder head, PtrUIHandler handler) {
        if (head == null || handler == null) {
            return head;
        }

        PtrUIHandlerHolder current = head;
        PtrUIHandlerHolder pre = null;
        do {

            // delete current: link pre to next, unlink next from current;
            // pre will no change, current move to next element;
            if (current.containsHeader(handler)) {
                current.mHeaderHandler = null;
                if (current.mFooterHandler == null) {
                    // current is head
                    if (pre == null) {
                        head = current.mNext;
                        current.mNext = null;

                        current = head;
                    } else {

                        pre.mNext = current.mNext;
                        current.mNext = null;
                        current = pre.mNext;
                    }
                }
            } else {
                pre = current;
                current = current.mNext;
            }

        } while (current != null);

        if (head == null) {
            head = new PtrUIHandlerHolder();
        }
        return head;
    }
    public static PtrUIHandlerHolder removeFooterHandler(PtrUIHandlerHolder head, PtrUIHandler handler) {
        if (head == null || handler == null) {
            return head;
        }

        PtrUIHandlerHolder current = head;
        PtrUIHandlerHolder pre = null;
        do {

            // delete current: link pre to next, unlink next from current;
            // pre will no change, current move to next element;
            if (current.containsFooter(handler)) {
                current.mFooterHandler = null;
                if (current.mHeaderHandler == null) {
                    // current is head
                    if (pre == null) {
                        head = current.mNext;
                        current.mNext = null;

                        current = head;
                    } else {

                        pre.mNext = current.mNext;
                        current.mNext = null;
                        current = pre.mNext;
                    }
                }
            } else {
                pre = current;
                current = current.mNext;
            }

        } while (current != null);

        if (head == null) {
            head = new PtrUIHandlerHolder();
        }
        return head;
    }

    @Override
    public void onUIReset(PtrFrameLayout frame) {
        PtrUIHandlerHolder current = this;
        do {
            final PtrUIHandler headerHandler = current.getHeaderHandler();
            final PtrUIHandler footerHandler = current.getFooterHandler();
            if (frame.getPtrIndicator().isPullDown())
            if (null != headerHandler) {
                headerHandler.onUIReset(frame);
            }
            if (null != footerHandler) {
                footerHandler.onUIReset(frame);
            }
        } while ((current = current.mNext) != null);
    }

    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {
        if (!hasHeaderHandler() && !hasFooterHandler()) {
            return;
        }
        PtrUIHandlerHolder current = this;
        do {
            final PtrUIHandler headerHandler = current.getHeaderHandler();
            final PtrUIHandler footerHandler = current.getFooterHandler();
            if (frame.getPtrIndicator().isPullDown()) {
                if (null != headerHandler) {
                    headerHandler.onUIRefreshPrepare(frame);
                }
            }else {
                if (null != footerHandler) {
                    footerHandler.onUIRefreshPrepare(frame);
                }
            }
        } while ((current = current.mNext) != null);
    }

    @Override
    public void onUIRefreshBegin(PtrFrameLayout frame) {
        PtrUIHandlerHolder current = this;
        do {
            final PtrUIHandler headerHandler = current.getHeaderHandler();
            final PtrUIHandler footerHandler = current.getFooterHandler();
            if (frame.getPtrIndicator().isPullDown()) {
                if (null != headerHandler) {
                    headerHandler.onUIRefreshBegin(frame);
                }
            } else {
                if (null != footerHandler) {
                    footerHandler.onUIRefreshBegin(frame);
                }
            }
        } while ((current = current.mNext) != null);
    }

    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame) {
        PtrUIHandlerHolder current = this;
        do {
            final PtrUIHandler headerHandler = current.getHeaderHandler();
            final PtrUIHandler footerHandler = current.getFooterHandler();
            if (frame.getPtrIndicator().isPullDown()) {
                if (null != headerHandler) {
                    headerHandler.onUIRefreshComplete(frame);
                }
            } else {
                if (null != footerHandler) {
                    footerHandler.onUIRefreshComplete(frame);
                }
            }
        } while ((current = current.mNext) != null);
    }

    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {
        PtrUIHandlerHolder current = this;
        do {
            final PtrUIHandler headerHandler = current.getHeaderHandler();
            final PtrUIHandler footerHandler = current.getFooterHandler();
            if (ptrIndicator.isPullDown()) {
                if (null != headerHandler) {
                    headerHandler.onUIPositionChange(frame, isUnderTouch, status, ptrIndicator);
                }
            } else{
                if (null != footerHandler) {
                    footerHandler.onUIPositionChange(frame, isUnderTouch, status, ptrIndicator);
                }
            }
        } while ((current = current.mNext) != null);
    }

}
