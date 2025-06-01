package online.tufactura.api.domain;

public enum FlowStateEnum {
    INITIAL,


    INITIAL_SIGN_UP,
    SIGN_UP_NAME,
    SIGN_UP_EMAIL,
    SIGN_UP_COMPANY_NAME, // This state indicates that the user is providing their company name during the sign-up process or "razón social"
    SIGN_UP_COMPLETE,


    //INVOICING STATES
    INITIAL_INVOICE, // This state indicates the start of the invoicing process
    INITIAL_TEXT_INVOICE,
    INITIAL_AUDIO_INVOICE,
    EASY_INVOICE,
    INITIAL_STATE_INVOICING,
    PROCESSING_AUDIO,
    INVOICE_SENT,
    PROCESSING_TEXT, // this state indicates that after transcribing the audio, the text is being processed (Calling the GPT API, etc.)


    //FINISHED STATES
    FINISHED, // This state indicates that the flow has been completed successfully
    FINISHED_ERROR,
    ; // This state indicates that the flow has ended with an error

    public boolean isFinalState() {
        return this == FINISHED || this == FINISHED_ERROR;
    }
}
